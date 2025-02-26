package korweb.service;

import korweb.model.dto.ProductDto;
import korweb.model.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductService {
    @Autowired private ProductMapper productMapper;

    // 제품 등록
    public int save(ProductDto productDto) {return productMapper.save(productDto);}

    // 제품 전체 조회
    public List<ProductDto> findAll() {return productMapper.findAll();}

    // 제품 개별 조회
    public ProductDto find(int id) {return productMapper.find(id);}

    // 제품 수정
    public boolean update(ProductDto productDto) {return productMapper.update(productDto);}

    // 제품 삭제
    public boolean delete(int id) {return productMapper.delete(id);}
}
