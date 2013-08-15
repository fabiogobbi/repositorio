package com.kwikemart.cliente.repository.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonIgnore;


@Entity
@Table(schema="kwikemart", name="cliente")
public class Cliente implements Serializable{
    
    private static final long serialVersionUID = 5064858987632395334L;

    @NotNull(message="Preencha o e-mail.")
    @Pattern(regexp=".+@.+\\.[a-z]+", message="E-mail invalido.")
    private String email;
    
    @NotNull(message="Preencha a senha.")
    @Pattern(regexp="(?i)^[a-zA-Z0-9 ]{6,8}$", message="Senha invalida.")
    private String senha;
    
    @Pattern(regexp="(?i)^[a-zA-Z0-9 ]{6,8}$", message="Confirmacao de senha invalida.")
    private String confirmaSenha;
    
    @NotNull (message="Preencha o nome.")
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,20}", message="Nome invalido.")
    private String nome;
    
    @NotNull(message="Preencha o sobrenome.")
    @Pattern(regexp="(?i)^[a-z A-Z0-9éúíóáÉÚÍÓÁèùìòàÈÙÌÒÀõãñÕÃÑêûîôâÊÛÎÔÂëÿüïöäËYÜÏÖÄ\\s\\'\\-]{0,40}", message="Sobrenome invalido.")
    private String sobreNome;
    
    @NotNull (message="Preencha o cpf.")
    @Pattern(regexp="(?i)^[0-9]{0,11}", message="CPF invalido. Digite apenas numeros.")
    private String cpf;
    
    @NotNull(message="Preencha a data de nascimento.")
    @Temporal(value=TemporalType.DATE)
    @Past(message="Data de nascimento invalida.")
    private Date dataNascimento;
    
    @NotNull(message="Preencha o sexo.")
    @Min(value=1L) @Max(value=2L)
    private Integer sexo;
    
    private int tipoAcesso;
    private boolean ativo;
    private Set<Endereco> enderecos;
    private Perfil perfil;
    
    @Id
    @Column(name="email", nullable=false, columnDefinition="VARCHAR(45)")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
   
    @Column(name="senha", nullable=false, columnDefinition="VARCHAR(10)")
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    @Column(name="nome", nullable=false, columnDefinition="VARCHAR(20)")
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Column(name="sobreNome", nullable=false, columnDefinition="VARCHAR(40)")
    public String getSobreNome() {
        return sobreNome;
    }
    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }
    
    @Column(name="cpf", nullable=true, columnDefinition="VARCHAR(11)")
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @Column(name="dataNascimento", nullable=false, columnDefinition="DATE")
    public Date getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    @Column(name="sexo", nullable=false, columnDefinition="INT")
    public Integer getSexo() {
        return sexo;
    }
    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }
    
    @Column(name="ativo", nullable=false, columnDefinition="VARCHAR(5)")
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    public Set<Endereco> getEnderecos() {
        return enderecos;
    }
    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    
    @Transient 
	public Endereco getEnderecoPorId(long id) {
    	Endereco endereco = null;
    	for(Iterator<Endereco> it = enderecos.iterator(); it.hasNext(); ){
    		endereco = it.next();
    		if(endereco.getId() == id){
    			break;
    		}
    	}
    	return endereco;
	}
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="codPerfil", nullable = false)      
    public Perfil getPerfil() {
        return perfil;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    @Transient
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	@Column(name="tipoAcesso", nullable=false, columnDefinition="INT")
	public int getTipoAcesso() {
		return tipoAcesso;
	}
	public void setTipoAcesso(int tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}
}
