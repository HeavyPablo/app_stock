package com.restaurante.app_stock.service;

import com.restaurante.app_stock.models.Producto;
import com.restaurante.app_stock.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Producto findById(int id) {
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    public String delete(Producto producto) {
        try {
            productoRepository.delete(producto);
            return "ok";
        } catch (Exception e) {
            return "error";
        }

    }
}
