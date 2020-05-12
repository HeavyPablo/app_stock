package com.restaurante.app_stock.controllers;

import com.restaurante.app_stock.models.Categoria;
import com.restaurante.app_stock.models.Producto;
import com.restaurante.app_stock.repository.CategoriaRepository;
import com.restaurante.app_stock.repository.ProductoRepository;
import com.restaurante.app_stock.service.CategoriaService;
import com.restaurante.app_stock.service.ProductoService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public ModelAndView home() {

        List<Producto> productos = productoService.findAll();
        ModelAndView layout = new ModelAndView("index");

        layout.addObject("productos", productos);
        layout.addObject("view", "home");

        return layout;
    }

    // Productos

    @GetMapping("/productos/listar")
    public ModelAndView listar() {

        List<Producto> productos = productoService.findAll();
        int productosCount = productos.size();
        ModelAndView layout = new ModelAndView("index");

        layout.addObject("view", "listar");
        layout.addObject("productos", productos);
        layout.addObject("productosCount", productosCount);

        return layout;
    }

    @GetMapping("/productos/agregar")
    public ModelAndView agregar() {

        ModelAndView layout = new ModelAndView("index");

        List<Categoria> categorias = categoriaService.findAll();

        layout.addObject("producto", new Producto());
        layout.addObject("view", "agregar");
        layout.addObject("categorias", categorias);

        return layout;
    }

    @PostMapping("/productos/agregar/post")
    public String agregarPost(@Valid Producto producto) {


        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        producto.setFechaActualizacion(dateFormat.format(date));
        producto.setFechaCreacion(dateFormat.format(date));

        productoService.save(producto);

        return "redirect:/productos/listar";
    }

    @GetMapping("/productos/{id}")
    public ModelAndView ver(@PathVariable String id) {
        Producto producto = productoService.findById(Integer.parseInt(id));
        List<Categoria> categorias = categoriaService.findAll();

        ModelAndView layout = new ModelAndView("index");

        layout.addObject("view", "ver");
        layout.addObject("producto", producto);
        layout.addObject("categorias", categorias);

        return layout;
    }

    @GetMapping("/productos/editar/{id}")
    public ModelAndView editar(@PathVariable String id) {
        Producto producto = productoService.findById(Integer.parseInt(id));
        ModelAndView layout = new ModelAndView("index");
        List<Categoria> categorias = categoriaService.findAll();

        layout.addObject("producto", producto);
        layout.addObject("newProducto", new Producto());
        layout.addObject("categorias", categorias);

        layout.addObject("view", "editar");

        return layout;
    }

    @PostMapping("/productos/editar/post/{id}")
    public String editarPost(@Valid Producto producto, @PathVariable String id) {

        try {
            Producto newProducto = productoService.findById(Integer.parseInt(id));

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            newProducto.setCategoria(producto.getCategoria());
            newProducto.setCantidad(producto.getCantidad());
            newProducto.setDescripcion(producto.getDescripcion());
            newProducto.setNombre(producto.getNombre());
            newProducto.setFechaActualizacion(dateFormat.format(date));

            productoService.save(newProducto);

            return "redirect:/productos/listar";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        Producto producto = productoService.findById(Integer.parseInt(id));
        String response = productoService.delete(producto);
        if (response.equals("ok")) {
            return "redirect:/productos/listar";
        }
        return "redirect:/";
    }
}
