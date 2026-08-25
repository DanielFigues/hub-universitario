
# AI_USAGE



# Declaração de Uso de Inteligência Artificial



## Ferramentas e modelos utilizados



* **Google Gemini**: Utilizado como parceiro de pareamento (pair programming), apoio na investigação de bugs, revisão de sintaxe e orientação sobre o fluxo de versionamento.



## Etapas em que a IA foi utilizada



* Resolução de problemas de integridade de dados e concorrência no banco de dados.


* Implementação de regras de validação de dados de entrada no backend.


* Implementação da correção dos códigos de status HTTP devolvidos pela API.


* Esclarecimento de dúvidas sobre comandos e melhores práticas de fluxos no Git/GitHub (criação de issues, resolução de travas no build/node_modules e vinculação de Pull Requests).


* Nivelamento na linguagem Java e ecossistema Spring Boot, acelerando a compreenção de sintaxes avançadas, anotações do framework e padrões de projeto.


* Correção de testes automatizados do React lidando com elementos duplicados na DOM e seletores assíncronos.


* Configuração e estruturação da esteira de Integração Contínua (CI/CD) com GitHub Actions (`ci.yml`), além do ajuste de ferramentas de análise estática e cobertura (`SpotBugs` e `JaCoCo`).

* Resolução de cenários avançados de controle de versão, como limpeza de cache de rastreamento do Git para arquivos binários (`.class`), gerenciamento do `.gitignore` e sincronização de branch com os merges recentes da equipe.

## Resumo dos principais prompts ou objetivos solicitados



* "Como impedir inscrições acima do limite e resolver problemas de concorrência em requisições simultâneas no Spring Boot?"


* "Qual a melhor expressão regular (Regex) no Java/Bean Validation para barrar números e caracteres especiais no campo de nome?"


* "Quais os status HTTP mais adequados para refletir violações de regras de negócio, como tentar se inscrever em uma atividade `FULL` ou enviar dados inválidos?"


* "Por que os comandos de checkout e pull falham ao identificar alterações não rastreadas na pasta node_modules?"


* "Como estruturar os filtros de busca no React mantendo o estado correto sem recarregar a página?"


* "Por que o waitFor do Testing Library está estourando timeout no teste de autocomplete da página de atividades?"


* "Como configurar um pipeline no GitHub Actions para automatizar os testes e o build do backend com Maven?"

* "Como remover arquivos compilados (`target/`) e dependências (`node_modules/`) do controle de versão sem apagá-los do disco usando o `.gitignore` e o cache do Git?"


## Arquivos ou partes da solução influenciados



* **Backend:** Classes de DTO associadas à validação de inscrições e Handlers globais para mapeamento de exceções e status HTTP.


* **Frontend:** Componentes e hooks encarregados do estado da listagem de atividades e submissão do formulário de inscrição.


* **Documentação:** Formatação padronizada de Pull Requests e de Issues.


* **Infraestrutura e Build:** Arquivo de workflow do GitHub Actions (`.github/workflows/ci.yml`), arquivo de exclusões do SpotBugs (`spotbugs-exclude.xml`), regras do JaCoCo no `pom.xml` e configuração do arquivo `.gitignore`.


## Sugestões aceitas, adaptadas ou rejeitadas



* **Aceitas:** Adoção da anotação `@Pattern` com a regex fornecida para sanitização no DTO, o que isola a camada de apresentação do Service. Aplicação dos status HTTP corretos sugeridos (`409 Conflict` para atividade lotada, `400 Bad Request` para dados incorretos).


* **Adaptadas:** As sugestões de arquitetura para controle de concorrência foram adaptadas para utilizar apenas os recursos nativos do Spring Data JPA e do H2. Ferramentas, dependências externas pesadas ou atalhos superficiais de resolução sugeridas pela IA foram rejeitadas para respeitar estritamente os fluxo lógicos dos algoritmos e a simplicidade exigida pelo `PROJECT.md`.


* **Ajuste de Status HTTP de Erro:** Na revisão de uma correção, foi sugerido pela IA o retorno de status 404 Not Found para tratar a tentativa de re-inscrição. Após a leitura e análise do PROJECT.md, a sugestão foi adaptada para 409 Conflict, garantindo que as regras de negócio não fossem descumpridas.


* **Infraestrutura e Git:** Aceita a adoção de um arquivo `.gitignore` estruturado e adaptados os comandos de limpeza de cache de rastreamento (`git rm --cached`) para remover artefatos locais do build do histórico do Git com segurança.


## Como o participante revisou e validou o resultado



* Nenhuma sugestão de código foi adotada sem análise prévia de impacto na arquitetura atual.


* As validações de Regex e o bloqueio de “lotação fantasma” foram testados intensamente via interface do usuário e verificados de forma independente via console do banco H2 (`/h2-console`).


* A suíte de testes existente (`./mvnw test` e `npm test`) foi executada continuamente para garantir que os novos tratamentos de exceção e filtros não quebrassem os fluxos de sucesso descritos no escopo inicial.


* Além dos testes predefinidos, também houveram diversas execuções experimentais práticas com o propósito de garantir a estabilidade, corretude e validação de resultados das implementações feitas.


* Validação local rigorosa do build através de execuções de verificação de qualidade (`./mvnw clean verify`), garantindo que a compilação, o SpotBugs, o JaCoCo e o fluxo de integração estejam perfeitos antes do commit e do push.
