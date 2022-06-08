package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJDBCUpdate {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {

                Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
                System.out.println("============== Listar ==============");
                repositorio.listar().forEach(System.out::println);

                System.out.println("============== Obtener por id ==============");
                System.out.println(repositorio.porId(1L));

                System.out.println("============== Editar producto ==============");
                Producto producto = new Producto();
                producto.setId(18L);
                producto.setNombre("Refrigerador Samsung");
                producto.setPrecio(9990);
                Categoria categoria = new Categoria();
                categoria.setId(3L);
                producto.setCategoria(categoria);
                producto.setSku("Abcdefg123");
                repositorio.guardar(producto);
                System.out.println("Producto Editado con Exito");
                repositorio.listar().forEach(System.out::println);

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
