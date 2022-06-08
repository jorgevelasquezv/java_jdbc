package co.com.diplomado.jdbc;

import co.com.diplomado.jdbc.models.Categoria;
import co.com.diplomado.jdbc.models.Producto;
import co.com.diplomado.jdbc.servicio.CatalogoServicio;
import co.com.diplomado.jdbc.servicio.Servicio;
import co.com.diplomado.jdbc.util.ConexionBaseDatos;
import repositorio.ProductoRepositorioImpl;
import repositorio.Repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJDBCUpdate {
    public static void main(String[] args) throws SQLException {

        Servicio servicio = new CatalogoServicio();

        System.out.println("============== Listar ==============");
        servicio.listar().forEach(System.out::println);

        System.out.println("============== Obtener por id ==============");
        System.out.println(servicio.porId(1L));

        System.out.println("============== Editar producto ==============");
        Producto producto = new Producto();
        producto.setId(18L);
        producto.setNombre("Refrigerador Samsung");
        producto.setPrecio(8990);
        Categoria categoria = new Categoria();
        categoria.setId(3L);
        producto.setSku("Abcdefg123");
        servicio.guardarProductoConCategoria(producto, categoria);
        System.out.println("Producto Editado con Exito");
        servicio.listar().forEach(System.out::println);
    }
}
