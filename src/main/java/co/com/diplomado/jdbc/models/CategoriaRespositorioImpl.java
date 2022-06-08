package co.com.diplomado.jdbc.models;

import repositorio.Repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRespositorioImpl implements Repositorio<Categoria> {

    private Connection conn;

    public CategoriaRespositorioImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try(Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM categorias")) {
            while (resultSet.next()){
                categorias.add(crearCategoria(resultSet));
            }
        }
        return categorias;
    }

    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        try(PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM categorias as c WHERE c.id=?")) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    categoria = crearCategoria(resultSet);
                }
            }
        }
        return categoria;
    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
        String sql = null;
        if (categoria.getId() != null && categoria.getId() > 0){
            sql = "UPDATE categorias SET nombre=? WHERE id=? ";
        }else {
            sql = "INSERT INTO categorias(nombre) VALUES(?)";
        }
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, categoria.getNombre());
            if (categoria.getId() != null && categoria.getId() > 0){
                preparedStatement.setLong(2, categoria.getId());
            }
            preparedStatement.executeUpdate();
            if (categoria.getId() == null){
                try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()){
                        categoria.setId(resultSet.getLong(1));
                    }
                }
            }
        }
        return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM categorias WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    private Categoria crearCategoria(ResultSet resultSet) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("id"));
        categoria.setNombre(resultSet.getString("nombre"));
        return categoria;
    }
}
