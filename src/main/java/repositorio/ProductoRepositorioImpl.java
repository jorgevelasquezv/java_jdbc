package repositorio;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImpl implements Repositorio<Producto> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();

        try (Statement statement = getConnection().createStatement();
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

        try (PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT p.*, c.nombre  as categoria FROM products as p " +
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
    public void guardar(Producto producto) {
        String sql;
        if (producto.getId() != null && producto.getId() > 0 ){
            sql = "UPDATE products SET nombre = ?, precio = ?, id_categoria = ? WHERE id = ?";
        }else {
            sql = "INSERT INTO products(nombre, precio, id_categoria, fecha_registro) VALUES(?, ?, ?, ?)";
        }
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)){
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setLong(2, producto.getPrecio());
            preparedStatement.setLong(3, producto.getCategoria().getId());
            if (producto.getId() != null && producto.getId() > 0 ){
                preparedStatement.setLong(4, producto.getId());
            }else {
                preparedStatement.setDate(3, new Date(producto.getFechaRegistro().getTime()));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Long id) {
        try (PreparedStatement preparedStatement= getConnection().prepareStatement("DELETE FROM products WHERE id=?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Producto crearProducto(ResultSet resultSet) throws SQLException {
        Producto producto = new Producto();
        producto.setId(resultSet.getLong("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFechaRegistro(resultSet.getDate("fecha_registro"));
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("id_categoria"));
        categoria.setNombre(resultSet.getString("categoria"));
        producto.setCategoria(categoria);
        return producto;
    }
}
