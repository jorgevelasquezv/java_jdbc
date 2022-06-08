package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.servicio.CatalogoServicio;
import co.com.diplomado.jdbc.servicio.Servicio;

import java.sql.*;
import java.util.Date;

public class EjemploJDBC {
    public static void main(String[] args) throws SQLException {

        Servicio servicio = new CatalogoServicio();

        System.out.println("============== Listar ==============");
        servicio.listar().forEach(System.out::println);
        Categoria categoria = new Categoria();
        categoria.setNombre("Iluminacion");

        System.out.println("============== Agregar producto con categoria ==============");
        Producto producto = new Producto();
        producto.setNombre("Lampara led escritorio");
        producto.setPrecio(440);
        producto.setFechaRegistro(new Date());
        producto.setSku("Abcdeh126");
        servicio.guardarProductoConCategoria(producto, categoria);

        System.out.println("Producto guardado con exito: " + producto.getId());
        System.out.println("============== Listar ==============");
        servicio.listar().forEach(System.out::println);
    }
}
