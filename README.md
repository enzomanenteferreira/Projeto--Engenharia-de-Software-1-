# Projeto--Engenharia-de-Software-1-
Repositório destinado ao desenvolvimento do trabalho prático das etapas T3 e T4 da disciplina **Engenharia de Software I**, conforme especificações do curso.

## 📌 Estudo de Caso Escolhido

- [ ] Estudo de Caso 1 – Ordem de Serviço (OS)  
- [x] Estudo de Caso 2 – Receita Médica  

---

## 🔧 Estrutura do Projeto

### 🔹 T3A – Modelagem OO do Estudo de Caso

- Diagrama de Casos de Uso
- Diagrama de Classes de Negócio
- Modelo Entidade-Relacionamento (E-R)
- Classes de negócio implementadas em Java (`MyEnderecoBO`)

### 🔹 T3B – Implementação de Serviços de Endereço

Serviços REST implementados na classe `UCEnderecoGeralServicos`, conforme arquitetura N-camadas:

| Serviço | Descrição |
|--------|-----------|
| `cadastrarEndereco` | Cadastrar novo endereço com validações (CEP, campos obrigatórios etc) |
| `obterEnderecoPorCEP` | Buscar endereço por CEP |
| `obterEnderecoPorID` | Buscar endereço por ID |
| `obterEnderecoExterno` | Buscar endereço a partir de fonte externa (ex: API) |
| `obterCidade` | Buscar cidade pelo nome |

> Projeto dividido em pacotes `bo`, `dao`, `col`, `manager`, `infra`.

### 🔹 T3C – Implementação do Estudo de Caso (Cadastro de Paciente)

- Backend: Serviços de cadastro e consulta de pacientes
- Frontend: Interface web para cadastro e consulta de pacientes
- Integração com os serviços de endereço implementados no T3B

### 🔹 T4A – Implementação dos Serviços de Receita Médica

- Backend: Serviços `cadastrarReceita` e `consultarReceita`
- Frontend: Interface para cadastro e consulta de receitas
- Dados tratados incluem: paciente, médico, medicamentos, posologia, data, CID etc.

### 🔹 T4B – Implementação com Inteligência Artificial (IA)

- Componente de IA capaz de interpretar comandos textuais do usuário, como:
  - "Quero consultar a receita número 456"
  - "Me diga os dados do paciente CPF 123"
- A IA realiza chamadas aos serviços existentes (sem acesso direto ao banco)
- Interface textual para testes

---

## 🛠 Tecnologias Utilizadas

- **Java**
- **Arquitetura N-Camadas**
- **Servlets / REST API**
- **HTML/CSS/JS** ou framework frontend à escolha
- **SGBD com Pool de Conexões**
- **Bibliotecas de NLP / IA** (para T4B)

---
