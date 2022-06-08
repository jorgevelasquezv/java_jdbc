package co.com.diplomado.jdbc.servicio;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;

import java.sql.SQLException;
import java.util.List;

public interface Servicio {

    List<Producto> listar() throws SQLException;

    Producto porId(Long id) throws SQLException;

    Producto guardar(Producto producto) throws SQLException;

    void eliminar(Long id) throws SQLException;

    List<Categoria> listarCategorias() throws SQLException;

    Categoria porIdCategoria(Long id) throws SQLException;

    Categoria guardarCategoria(Categoria categoria) throws SQLException;

    void eliminarCategoria(Long id) throws SQLException;

    void guardarProductoConCategoria(Producto producto, Categoria categoria) throws SQLException;

}
