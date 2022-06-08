package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.CategoriaRespositorioImpl;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.*;
import java.util.Date;

public class EjemploJDBC {
    public static void main(String[] args) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            if (conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try{
                Repositorio<Categoria> categoriaRepositorio = new CategoriaRespositorioImpl(conn);
                System.out.println("============== Insertar nueva Categoria ==============");
                Categoria categoria = new Categoria();
                categoria.setNombre("Electrohogar");
                Categoria nuevaCategoria = categoriaRepositorio.guardar(categoria);
                System.out.println("Categoria guardada con exito: " + nuevaCategoria.getId());

                Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
                System.out.println("============== Listar ==============");
                repositorio.listar().forEach(System.out::println);

                System.out.println("============== Obtener por id ==============");
                System.out.println(repositorio.porId(1L));

                System.out.println("============== Insertar nuevo producto ==============");
                Producto producto = new Producto();
                producto.setNombre("Refrigerador Samsung");
                producto.setPrecio(9900);
                producto.setFechaRegistro(new Date());
                producto.setSku("abcdefg123");

                producto.setCategoria(nuevaCategoria);
                repositorio.guardar(producto);
                System.out.println("Producto Guardado con Exito: " + producto.getId());
                repositorio.listar().forEach(System.out::println);

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
