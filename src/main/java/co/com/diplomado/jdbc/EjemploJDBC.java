package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.*;
import java.util.Date;

public class EjemploJDBC {
    public static void main(String[] args) {

        try{

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("============== Listar ==============");
            repositorio.listar().forEach(System.out::println);

            System.out.println("============== Obtener por id ==============");
            System.out.println(repositorio.porId(1L));

            System.out.println("============== Insertar nuevo producto ==============");
            Producto producto = new Producto();
            producto.setNombre("Teclado Mecanico");
            producto.setPrecio(500);
            Categoria categoria = new Categoria();
            categoria.setId(1L);
            producto.setCategoria(categoria);
            producto.setFechaRegistro(new Date());
            repositorio.guardar(producto);
            System.out.println("Producto Guardado con Exito");
            repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
