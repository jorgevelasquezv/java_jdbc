package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.servicio.CatalogoServicio;
import co.com.diplomado.jdbc.servicio.Servicio;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;

public class EjemploJDBCDelete {
    public static void main(String[] args) throws SQLException {

        Servicio servicio = new CatalogoServicio();
        System.out.println("============== Listar ==============");
        servicio.listar().forEach(System.out::println);

        System.out.println("============== Obtener por id ==============");
        System.out.println(servicio.porId(1L));

        System.out.println("============== Eliminar producto ==============");
        Producto producto = new Producto();
        servicio.eliminar(6L);
        System.out.println("Producto Eliminado con Exito");
        servicio.listar().forEach(System.out::println);
    }
}
