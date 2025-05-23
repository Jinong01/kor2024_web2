package korweb.controller;

import korweb.model.dto.ProductDto;
import korweb.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired private ProductService productService;

    // 제품 등록
    @PostMapping("/product/save.do")
    public int save(@RequestBody ProductDto productDto){return productService.save(productDto);}

    // 제품 전체 조회
    @GetMapping("/product/findall.do")
    public List<ProductDto> findAll(){return productService.findAll();}

    // 제품 개별 조회
    @GetMapping("/product/find.do")
    public ProductDto find(@RequestParam int id){return productService.find(id);}

    // 제품 수정
    @PutMapping("/product/update.do")
    public boolean update(@RequestBody ProductDto productDto){return productService.update(productDto);}

    // 제품 삭제
    @DeleteMapping("/product/delete.do")
    public boolean delete(@RequestParam int id){return productService.delete(id);}
}
