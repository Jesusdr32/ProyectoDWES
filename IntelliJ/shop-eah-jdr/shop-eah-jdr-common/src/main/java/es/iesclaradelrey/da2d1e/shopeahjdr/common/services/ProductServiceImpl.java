package es.iesclaradelrey.da2d1e.shopeahjdr.common.services;


import es.iesclaradelrey.da2d1e.shopeahjdr.common.dto.web.NewProductsDto;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Brand;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.BrandNotFoundException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.exceptions.CategoryNotFoundException;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.mappers.ProductMapper;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.BrandRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.CategoryRepository;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.stream.*;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by("name").ascending());
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createNew(NewProductsDto newProductsDto) {

        Set<Category> categories = Set.copyOf(categoryRepository.findAllById(newProductsDto.getCategories()));

        Brand brand = brandRepository.findById(newProductsDto.getBrandId()).orElseThrow(() -> new EntityNotFoundException(String.format("La desarrolladora con id %s no existe", newProductsDto.getBrandId())));

        if (categories.size()!= newProductsDto.getCategories().size()){
            throw new EntityNotFoundException("Alguno de los módulos no se han encontrado");
        }

        if (newProductsDto.getProductImage().isEmpty()) {
            newProductsDto.setProductImage(null);
        }

        Product product = productMapper.map(newProductsDto);
        product.setBrand(brand);
        product.setCategories(categories);

        return productRepository.save(product);
    }

    @Override
    public Product update(Long productId, NewProductsDto newProductsDto) {
        if (newProductsDto.getProductImage().isEmpty()) {
            newProductsDto.setProductImage(null);
        }

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("El producto con id %s no existe", productId)));

        // Actualizamos
        product.setEan(newProductsDto.getProductEan());
        product.setName(newProductsDto.getProductName());
        product.setDescription(newProductsDto.getProductDescription());
        product.setImage(newProductsDto.getProductImage());
        product.setPrice(newProductsDto.getProductPrice());
        product.setDiscount(newProductsDto.getProductDiscount());

        // Actulizamos el brand ya que estamos en el lado propietario
        Brand brand = brandRepository.getReferenceById(newProductsDto.getBrandId());
        product.setBrand(brand);

        // Tambien es desde el lado propietario
        Set<Category> newCategories = new HashSet<>(categoryRepository.findAllById(newProductsDto.getCategories()));
        product.setCategories(newCategories);

        return productRepository.save(product);
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoriesId(categoryId, Sort.by("name").ascending());
    }

    //@Override
    //public void deleteById(Long id) {
        //productRepository.deleteById(id);
    //}

    @Override
    public String exportAllStax() throws XMLStreamException {
        // Obtener productos
        List<Product> products = productRepository.findAll();

        // Crear fábrica StAX
        XMLOutputFactory factory = XMLOutputFactory.newInstance();

        // Usamos StringWriter para devolver el XML como String
        StringWriter stringWriter = new StringWriter();

        // Crear wrtier XML
        XMLStreamWriter writer = factory.createXMLStreamWriter(stringWriter);

        // Escribir documento XML
        writer.writeStartDocument("1.0");

        // Elemento raíz
        writer.writeStartElement("products");

        // Exportar productos
        exportProducts(products, writer);

        // Cerrar raíz
        writer.writeEndElement();

        // Finalizar documento
        writer.writeEndDocument();

        writer.flush();
        writer.close();

        return stringWriter.toString();
    }

    @Override
    public void importProductsStax(InputStream productsStream) throws XMLStreamException {
        List<Product> products = readProductsFromXmlStax(productsStream);
        productRepository.saveAll(products);
    }


    private void exportProducts(List<Product> products, XMLStreamWriter writer) throws XMLStreamException {
        for (Product product : products) {
            writer.writeStartElement("product");

            // Atributos principales
            writer.writeAttribute("productId", String.valueOf(product.getId()));
            writer.writeAttribute("productEan", String.valueOf(product.getEan()));
            writer.writeAttribute("productPrice", String.valueOf(product.getPrice()));
            writer.writeAttribute("productDiscount", String.valueOf(product.getDiscount()));
            writer.writeAttribute("productStock", String.valueOf(product.getStock()));

            // Elemento nombre del producto
            writer.writeStartElement("productName");
            writer.writeCharacters(product.getName());
            writer.writeEndElement();

            // Elemento descripción del producto
            writer.writeStartElement("productDescription");
            writer.writeCharacters(product.getDescription());
            writer.writeEndElement();

            // Elemento marca del producto
            writer.writeStartElement("brand");
            writer.writeAttribute("brandId", String.valueOf(product.getBrand().getId()));

            // Elemento nombre de la marca
            writer.writeStartElement("brandName");
            writer.writeCharacters(product.getBrand().getName());
            writer.writeEndElement();

            // Elemento descripción de la marca
            writer.writeStartElement("brandDescription");
            writer.writeCharacters(product.getBrand().getDescription());
            writer.writeEndElement();

            writer.writeEndElement();

            //Elementos categorías del producto
            writer.writeStartElement("categories");
            for (Category category : product.getCategories()) {
                //Elemento categoría
                writer.writeStartElement("category");
                writer.writeAttribute("categoryId", String.valueOf(category.getId()));

                // Elemento nombre de la categoría
                writer.writeStartElement("categoryName");
                writer.writeCharacters(category.getName());
                writer.writeEndElement();

                // Elemento descripción de la categoría
                writer.writeStartElement("categoryDescription");
                writer.writeCharacters(category.getDescription());
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndElement();
        }
    }

    private List<Product> readProductsFromXmlStax(InputStream inputStream) throws XMLStreamException {
        List<Product> products = new ArrayList<>();

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

        Product currentProduct = null;
        Set<Category> currentCategories = null;

        while (reader.hasNext()) {
            reader.next();

            if (reader.isStartElement()) {
                String tag = reader.getLocalName();

                if ("product".equals(tag)) {
                    currentProduct = new Product();
                    setProductAttributesStax(reader, currentProduct);
                    currentCategories = new HashSet<>();
                } else if ("productName".equals(tag) && currentProduct != null) {
                    String text = reader.getElementText();
                    currentProduct.setName(text);
                } else if ("productDescription".equals(tag) && currentProduct != null) {
                    String text = reader.getElementText();
                    currentProduct.setDescription(text);
                } else if ("brand".equals(tag) && currentProduct != null) {
                    currentProduct.setBrand(
                            brandRepository.findById(Long.valueOf(reader
                                            .getAttributeValue(null, "brandId")))
                                    .orElseThrow(BrandNotFoundException::new));
                } else if ("category".equals(tag) && currentProduct != null) {
                    currentCategories.add(
                            categoryRepository.findById(Long.valueOf(reader
                                    .getAttributeValue(null, "categoryId")))
                                    .orElseThrow(CategoryNotFoundException::new));
                }
            } else if (reader.isEndElement()) {
                if ("product".equals(reader.getLocalName()) && currentProduct != null) {
                    currentProduct.setCategories(currentCategories);
                    products.add(currentProduct);
                    currentProduct = null;
                    currentCategories = null;
                }
            }
        }

        reader.close();
        return products;
    }

    private void setProductAttributesStax(XMLStreamReader reader, Product product) {
//        product.setId(Long.valueOf(reader.getAttributeValue(null, "productId")));
        product.setEan(reader.getAttributeValue(null, "productEan"));
        product.setPrice(Double.valueOf(reader.getAttributeValue(null, "productPrice")));
        product.setDiscount(Integer.valueOf(reader.getAttributeValue(null, "productDiscount")));
        product.setStock(Integer.valueOf(reader.getAttributeValue(null, "productStock")));
    }

}
