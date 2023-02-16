package learn.boardgames.controllers;

import learn.boardgames.domain.CategoryService;
import learn.boardgames.models.Category;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = { "http://localhost:3000" })
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }
}
