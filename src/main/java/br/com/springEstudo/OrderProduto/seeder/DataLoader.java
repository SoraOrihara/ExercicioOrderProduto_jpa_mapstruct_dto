package br.com.springEstudo.OrderProduto.seeder;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderItemEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.OrderStatus;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.PaymentMethod;
import br.com.springEstudo.OrderProduto.insfraestructure.entities.ProdutoEntity;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.OrderRepository;
import br.com.springEstudo.OrderProduto.insfraestructure.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;

@Component
public class DataLoader implements CommandLineRunner {
	private final ProdutoRepository productRepository;
	private final OrderRepository orderRepository;

	private final Faker faker = new Faker();
	private final Random random = new Random();

	public DataLoader(ProdutoRepository productRepository, OrderRepository orderRepository) {
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
	}

	@Override
    @Transactional
    public void run(String... args) throws Exception {
        // Limpa o banco de dados antes de popular
        orderRepository.deleteAll();
        productRepository.deleteAll();
        
        // 1. Cria 100 produtosO
        List<ProdutoEntity> products = new ArrayList<>();
        // Lista de categorias para atribuição aleatória
        List<String> categorias = List.of(
            "Eletrônicos", "Livros", "Vestuário", 
            "Alimentos", "Ferramentas", "Jogos", "Esportes"
        );
     // Define o locale para o Brasil
        Locale brazil = new Locale("pt", "BR");
        // Cria um formatador de números para esse locale
        NumberFormat nf = NumberFormat.getNumberInstance(brazil);
        for (int i = 0; i < 100; i++) {
        	try {
                ProdutoEntity product = new ProdutoEntity();
                product.setNome(faker.commerce().productName());
                product.setDescricao(faker.lorem().paragraph(1));
                // Atribui uma categoria aleatória
                product.setCategory(categorias.get(random.nextInt(categorias.size())));
                // Define o estoque do produto
                product.setEstoque(random.nextInt(191) + 10); // Estoque entre 10 e 200
                // Pega o preço como uma string
                String priceString = faker.commerce().price(50, 500);
                
                // Analisa a string para um número
                Number parsedPrice = nf.parse(priceString);
                
                // Converte o número para Double
                product.setPrice(parsedPrice.doubleValue());

                products.add(productRepository.save(product));
            } catch (ParseException e) {
                // Lidar com a exceção se a string não puder ser analisada
                System.err.println("Erro ao analisar o preço: " + e.getMessage());
            }
        }

        // 2. Cria 30 pedidos
        for (int i = 0; i < 30; i++) {
            OrderEntity order = new OrderEntity();
            order.setCustomer(faker.name().fullName()); 
            order.setShipping_adress(faker.address().fullAddress());
            order.setBilling_adress(faker.address().fullAddress());
            order.setPayment_method(PaymentMethod.values()[random.nextInt(PaymentMethod.values().length)]);
            order.setStatus(OrderStatus.values()[random.nextInt(OrderStatus.values().length)]);
            order.setCreatedAt(LocalDateTime.now().minusDays(random.nextInt(30)));
            
            OrderEntity savedOrder = orderRepository.save(order);

            double totalAmount = 0.0;
            // 3. Adiciona de 1 a 5 itens aleatórios por pedido
            for (int j = 0; j < random.nextInt(5) + 1; j++) {
                ProdutoEntity randomProduct = products.get(random.nextInt(products.size()));
                int quantity = random.nextInt(10) + 1;

                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrder(savedOrder);
                orderItem.setProduto(randomProduct);
                orderItem.setQuantity(quantity);
                orderItem.setPriceAtSale(randomProduct.getPrice());

                totalAmount += orderItem.getPriceAtSale() * orderItem.getQuantity();
                savedOrder.getItens().add(orderItem);
            }
            
            savedOrder.setTotalAmount(totalAmount);
            orderRepository.save(savedOrder);
        }
    }

}
