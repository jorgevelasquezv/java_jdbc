package repositorio;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    private Connection conn;

    public ProductoRepositorioImpl(Connection conn){
        this.conn = conn;
    }

//    private Connection getConnection() throws SQLException {
//        return ConexionBaseDatos.getInstance();
//    }

//    private Connection getConnection() throws SQLException {
//        return ConexionBaseDatos.getConnection();
//    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT p.*, c.nombre  as categoria FROM products as p " +
                     "INNER JOIN categorias as c ON (p.id_categoria = c.id)")){
            while (resultSet.next()){
                Producto producto = crearProducto(resultSet);
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) {
        Producto producto = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT p.*, c.nombre  as categoria FROM products as p " +
                "INNER JOIN categorias as c ON (p.id_categoria = c.id) WHERE p.id = ?")){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    producto = crearProducto(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    @Override
    public Producto guardar(Producto producto) throws SQLException {
        String sql;
        if (producto.getId() != null && producto.getId() > 0 ){
            sql = "UPDATE products SET nombre = ?, precio = ?, id_categoria = ? , sku = ? WHERE id = ?";
        }else {
            sql = "INSERT INTO products(nombre, precio, id_categoria, sku, fecha_registro) VALUES(?, ?, ?, ?, ?)";
        }
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) ){
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setLong(2, producto.getPrecio());
            preparedStatement.setLong(3, producto.getCategoria().getId());
            preparedStatement.setString(4, producto.getSku());
            if (producto.getId() != null && producto.getId() > 0 ){
                preparedStatement.setLong(5, producto.getId());
            }else {
                preparedStatement.setDate(5, new Date(producto.getFechaRegistro().getTime()));
            }
            preparedStatement.executeUpdate();
            if (producto.getId() == null){
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()){
                        producto.setId(resultSet.getLong(1));
                    }
                }
            }
        }
        return producto;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement preparedStatement= conn.prepareStatement("DELETE FROM products WHERE id=?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Producto crearProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFechaRegistro(resultSet.getDate("fecha_registro"));
        producto.setSku(resultSet.getString("sku"));
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("id_categoria"));
        categoria.setNombre(resultSet.getString("categoria"));
        producto.setCategoria(categoria);
        return producto;
    }
}
