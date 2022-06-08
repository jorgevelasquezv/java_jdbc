package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJDBCDelete {
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

                System.out.println("============== Eliminar producto ==============");
                Producto producto = new Producto();
                repositorio.eliminar(5L);
                System.out.println("Producto Eliminado con Exito");
                repositorio.listar().forEach(System.out::println);

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
