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
    public static void main(String[] args) {

        try (Connection conn = ConexionBaseDatos.getInstance();) {

            Repositorio<Producto> repositorio = new ProductoRepositorioImpl();
            System.out.println("============== Listar ==============");
            repositorio.listar().forEach(System.out::println);

            System.out.println("============== Obtener por id ==============");
            System.out.println(repositorio.porId(1L));

            System.out.println("============== Editar producto ==============");
            Producto producto = new Producto();
            producto.setId(16L);
            producto.setNombre("Teclado Razer Mecanico");
            producto.setPrecio(700);
            Categoria categoria = new Categoria();
            categoria.setId(1L);
            producto.setCategoria(categoria);
            repositorio.guardar(producto);
            System.out.println("Producto Editado con Exito");
            repositorio.listar().forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}