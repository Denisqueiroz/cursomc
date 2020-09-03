package com.queiroz.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.queiroz.cursomc.domain.Categoria;
import com.queiroz.cursomc.domain.Cidade;
import com.queiroz.cursomc.domain.Cliente;
import com.queiroz.cursomc.domain.Endereco;
import com.queiroz.cursomc.domain.Estado;
import com.queiroz.cursomc.domain.Pagamento;
import com.queiroz.cursomc.domain.PagamentoComBoleto;
import com.queiroz.cursomc.domain.PagamentoComCartao;
import com.queiroz.cursomc.domain.Pedido;
import com.queiroz.cursomc.domain.Produto;
import com.queiroz.cursomc.domain.enums.EstadoPagamento;
import com.queiroz.cursomc.domain.enums.TipoCliente;
import com.queiroz.cursomc.repositories.CategoriaRepository;
import com.queiroz.cursomc.repositories.CidadeRepository;
import com.queiroz.cursomc.repositories.ClienteRepository;
import com.queiroz.cursomc.repositories.EnderecoRepository;
import com.queiroz.cursomc.repositories.EstadoRepository;
import com.queiroz.cursomc.repositories.PagamentoRepository;
import com.queiroz.cursomc.repositories.PedidoRepository;
import com.queiroz.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository ;
	
	@Autowired
	private ProdutoRepository produtoRepository ;
	
	@Autowired
	private EstadoRepository estadoRepository ;
	
	@Autowired
	private CidadeRepository cidadeRepository ;
	
	@Autowired
	private ClienteRepository clienteRepository ;
	
	@Autowired
	private EnderecoRepository enderecoRepository ;
	
	@Autowired
	private PedidoRepository pedidoRepository ;
	
	@Autowired
	private PagamentoRepository pagamentoRepository ;
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e Banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinage,");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
	
		
		
		
		
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
						
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Pualo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est1);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2,c3));
		
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3637891377", TipoCliente.PESSOAFISICA, null);
		
		cli1.getTelefones().addAll(Arrays.asList("2736323", "93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto", "Jardim","38220834", cli1, c1) ;
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro","38220812", cli1, c2) ;
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyy HH:mm");
		/*estancianciando o  pedido */
		Pedido ped1 = new Pedido(null,sdf.parse("10/02/202 10:35"),cli1, e1);
		Pedido ped2 = new Pedido(null,sdf.parse("30/08/202 10:35"),cli1, e2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO , ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2020 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
	}
	
	
	
	
	

}

