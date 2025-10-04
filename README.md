# Sistema de Gerenciamento de Clínica Médica

![Java](https://img.shields.io/badge/Java-11%2B-blue?logo=java&logoColor=white)
![GUI](https://img.shields.io/badge/GUI-Java%20Swing-orange)
![Persistence](https://img.shields.io/badge/Persistence-CSV%20Files-brightgreen)
![Status](https://img.shields.io/badge/Status-Concluído-success)

## 📄 Descrição do Projeto

O **Sistema de Gerenciamento de Clínica Médica** é uma aplicação desktop desenvolvida em Java durante a displicina de POO, sendo o trabalho final, projetada para simular um ambiente real de agendamento e gerenciamento de consultas. O sistema permite a interação de dois tipos de usuários — Médicos e Pacientes — cada um com funcionalidades específicas para suas necessidades, desde o agendamento de uma consulta até a avaliação do atendimento.

Este projeto foi desenvolvido como um trabalho acadêmico, com foco na aplicação de conceitos de **Programação Orientada a Objetos**, **padrões de arquitetura de software** e **manipulação de arquivos**.

## ✨ Funcionalidades Principais

O sistema possui um fluxo completo de interação para os usuários:

* **Autenticação de Usuários:** Sistema de login que diferencia `Médicos` e `Pacientes`, exibindo painéis de controle personalizados.
* **Gerenciamento de Consultas (Paciente):**
    * **Busca Inteligente de Médicos:** Filtra médicos por nome, especialidade e, crucialmente, pela cobertura do plano de saúde do paciente.
    * **Agendamento com Regras de Negócio:** Um médico só pode ter 3 consultas por dia. A partir do 4º agendamento, o paciente é alocado em uma **lista de espera**.
    * **Cancelamento e Promoção Automática:** Ao cancelar uma consulta, o primeiro paciente da lista de espera para aquele dia/médico é automaticamente promovido.
    * **Avaliação de Atendimento:** Após uma consulta ser marcada como "realizada", o paciente pode avaliá-la com uma nota de 1 a 5 estrelas e um comentário.
* **Gerenciamento da Agenda (Médico):**
    * **Visualização da Agenda Diária:** O médico tem acesso a um painel que exibe todas as suas consultas para o dia corrente.
    * **Realização da Consulta:** O médico pode registrar detalhes do atendimento (sintomas, tratamento) e marcar a consulta como "realizada".
    * **Geração de Faturamento:** Para pacientes sem plano de saúde, o sistema calcula e gera um valor a ser pago, baseado na especialidade do médico.

## 🏛️ Arquitetura do Sistema

O projeto foi estruturado em uma **arquitetura de 4 camadas** para garantir a separação de responsabilidades, alta coesão e baixo acoplamento, facilitando a manutenção e a escalabilidade do código.

* **`View`**: A camada de apresentação, responsável por toda a interação com o usuário. Construída com **Java Swing**, ela apenas exibe dados e captura eventos, delegando toda a lógica para a camada de Serviço.
* **`Service`**: O cérebro da aplicação. Contém todas as regras de negócio, validações e orquestra a comunicação entre a interface do usuário e a camada de acesso a dados.
* **`DAO (Data Access Object)`**: Camada de persistência, responsável por abstrair o acesso aos dados. Ela sabe como ler e escrever nos arquivos **CSV**, garantindo que o resto do sistema não precise se preocupar com os detalhes de armazenamento.
* **`Model`**: O coração do sistema. Contém as classes de entidade (`Medico`, `Paciente`, `Consulta`) que representam a estrutura de dados da aplicação.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 11+
* **Interface Gráfica:** Java Swing
* **Persistência de Dados:** Manipulação de Arquivos de Texto (CSV)
* **IDE:** IntelliJ IDEA / Apache NetBeans

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar o sistema localmente.

**Pré-requisitos:**
* Java JDK 11 ou superior instalado.
* Uma IDE Java (ex: IntelliJ, NetBeans, Eclipse).

**Passos:**
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/seu-usuario/nome-do-repositorio.git](https://github.com/seu-usuario/nome-do-repositorio.git)
    ```
2.  Abra o projeto na sua IDE de preferência.

3.  Na **pasta raiz** do projeto (no mesmo nível da pasta `src`), crie os arquivos de dados iniciais:

    <details>
      <summary>Clique para ver o conteúdo do <b>medicos.csv</b></summary>
    
      ```csv
      1;Dr. Carlos Andrade;Cardiologia;carlos;123;Plano A|Plano B
      2;Dra. Ana Marques;Dermatologia;ana;12-  3;Plano C
      3;Dr. Joao Pedro;Ortopedia;joao;123;Plano A|Plano C
      ```
    </details>

    <details>
      <summary>Clique para ver o conteúdo do <b>pacientes.csv</b></summary>
    
      ```csv
      101;Maria Silva;34;Plano A;maria;456
      102;Pedro Costa;45;não tenho;pedro;456
      103;Beatriz Lima;28;Plano C;bia;456
      ```
    </deta-  ils>

4.  Localize e execute o arquivo `Aplicacao.java` que está no pacote `com.suaempresa.clinicamedica.main`.

5.  A janela de login irá aparecer. Use as credenciais dos arquivos `.csv` para testar.

## 👨‍💻 Autor

Rian Vilanova
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/rianvlnv/)
[![Email](https://img.shields.io/badge/Email-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:rianvilanova@gmail.com)
