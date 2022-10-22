package com.dimas.product.controller;

import com.dimas.product.model.Product;
import com.dimas.product.service.ProductService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping
    public List<Product> getProduct(){
     return productService.getProduct();
 }

    @PostMapping
    public Product addProduct(@RequestBody Product product) throws Exception{

        Product saveProduct = Product.builder()
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .category(product.getCategory())
                .price(product.getPrice())
                .timeStamp(new Date().toString())
                .build();

        saveProduct = productService.addProduct(saveProduct);

        return saveProduct;
    }
    @PostMapping("/upload")
    public List<Product> importExcelProduct(@RequestParam("file") MultipartFile file) throws Exception {


        List<Product> productList = new ArrayList<>();


        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        System.out.println(worksheet.getPhysicalNumberOfRows());

        for (int index = 0; index <= worksheet.getPhysicalNumberOfRows();index++){
            if(index > 0){
                Product product =  new Product();

                XSSFRow row = worksheet.getRow(index);

                product.setId(null);
                product.setName(row.getCell(0).getStringCellValue());
                product.setPrice(row.getCell(1).getNumericCellValue());
                product.setCategoryId(String.valueOf(row.getCell(2).getNumericCellValue()).substring(0,1));
                product.setCategory(row.getCell(3).getStringCellValue());
                product.setTimeStamp(row.getCell(4).getStringCellValue());

                productList.add(product);
                productService.ImportProducts(productList);
            }




        }

        return productList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id){

        Product product = null;
        product  = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id,@RequestBody Product product){

        product = productService.updateProduct(id,product);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteProduct(@PathVariable("id") String id){

        boolean deleted = false;
        deleted = productService.deleteProduct(id);

        Map<String,Boolean> response =  new HashMap<>();
        response.put("deleted",deleted);


        return ResponseEntity.ok(response);
    }
}
