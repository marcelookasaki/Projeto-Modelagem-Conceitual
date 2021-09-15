package com.myo.estudoDeCaso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.myo.estudoDeCaso.domain.Categoria;
import com.myo.estudoDeCaso.domain.Cidade;
import com.myo.estudoDeCaso.domain.Cliente;
import com.myo.estudoDeCaso.domain.Endereco;
import com.myo.estudoDeCaso.domain.Estado;
import com.myo.estudoDeCaso.domain.Pagamento;
import com.myo.estudoDeCaso.domain.PagamentoBoleto;
import com.myo.estudoDeCaso.domain.PagamentoCartao;
import com.myo.estudoDeCaso.domain.Pedido;
import com.myo.estudoDeCaso.domain.Produto;
import com.myo.estudoDeCaso.domain.enums.EstadoPagamento;
import com.myo.estudoDeCaso.domain.enums.TipoCliente;
import com.myo.estudoDeCaso.repositories.CategoriaRepository;
import com.myo.estudoDeCaso.repositories.CidadeRepository;
import com.myo.estudoDeCaso.repositories.ClienteRepository;
import com.myo.estudoDeCaso.repositories.EnderecoRepository;
import com.myo.estudoDeCaso.repositories.EstadoRepository;
import com.myo.estudoDeCaso.repositories.PagamentoRepository;
import com.myo.estudoDeCaso.repositories.PedidoRepository;
import com.myo.estudoDeCaso.repositories.ProdutoRepository;

@SpringBootApplication
public class EstudoDeCasoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(EstudoDeCasoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 5000.00);
		Produto p2 = new Produto(null, "Impressora", 1000.00);
		Produto p3 = new Produto(null, "Mouse", 120.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Marcelo Okasaki", "myo@gmail.com", "152.602.068-86", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("97187-5153", "5058-0147"));
		
		Endereco end1 = new Endereco(null, "Rua Luis Guerreiro", "172", null, "Vila Santo Stéfano", 
				"04152-110", cli1, c2);
		Endereco end2 = new Endereco(null, "Rua Felipe Cardoso", "582", null, "Jardim da Saúde", 
				"04149-080", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
				
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("10/05/2021 10:35"), cli1, end2);
		Pedido ped2 = new Pedido(null, sdf.parse("26/04/2021 09:12"), cli1, end1);
		
		Pagamento pgt1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 1);
		ped1.setPagamento(pgt1);
		
		Pagamento pgt2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, 
				sdf.parse("26/05/2021 00:00"), null);
		ped2.setPagamento(pgt2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgt1, pgt2));
	}
}

