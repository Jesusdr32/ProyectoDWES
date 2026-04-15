package es.iesclaradelrey.da2d1e.shopeahjdr.api.controllers;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductService;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/xml")
public class XmlRestController {

    private final ProductService productService;


    public XmlRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(produces = "application/xml")
    public ResponseEntity<String> exportProducts() throws XMLStreamException {
        String xmlDocument = productService.exportAllStax();

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm"));

        String filename = "products-export." + timestamp + ".xml";

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Disposition", "attachment; filename=" + filename)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(xmlDocument);
    }

    @PostMapping
    public ResponseEntity<String> importProducts(@RequestParam("productsfile") MultipartFile productsfile) throws XMLStreamException, IOException {
        if (productsfile.isEmpty()) {
            return ResponseEntity.badRequest().body("No se ha recibido fichero");
        }
        if (productsfile.getSize() == 0) {
            return ResponseEntity.badRequest().body("El fichero recibido está vacío.");
        }
        productService.importProductsStax(productsfile.getInputStream());

        return ResponseEntity.ok("Fichero importado correctamente.");
    }
}
