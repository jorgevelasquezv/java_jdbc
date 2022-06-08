package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJDBCDelete {
    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getInstance();) {

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("============== Listar ==============");
            repositorio.listar().forEach(System.out::println);

            System.out.println("============== Obtener por id ==============");
            System.out.println(repositorio.porId(1L));

            System.out.println("============== Eliminar producto ==============");
            Producto producto = new Producto();
            repositorio.eliminar(3L);
            System.out.println("Producto Eliminado con Exito");
            repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
