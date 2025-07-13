# SCALING (escalonamento)

Escalar uma aplicação é uma meta geral da arquitetura e das equipes.

SaaS: Software as a Service (subscription)
 Usuários:  3 clinícas cada uma com +ou- 200 pacientes
          300 clinícas
         3000 clinícas

Efeitos:
    - slowdown (bottleneck --> gargalo)
    - DoS (denial of service --> above capacity)

VOLUME USO (EX:RPM (REQUESTS PER MINUTE))
           20 RPM
          200 RPM
        2.000 RPM
    2.000.000 RPM

Raros casos em que é **constante**.

Considerar os _picos_ (usage spike/peak)

```plain
                         /----\
                        /      \
                       /        \
                      /          \
     /------\        /            \
    /       --------/              \
----/
```

Onde é realizado o _deploy_ (implantação):

CPU, RAM, STORAGE, BANDWIDTH (LARG BANDA), ...

- on-premises (nas próprias instalações) - bare metal (na máquina real)
- shared hosting (comum para websites) - problema _noisy neighbor_
- VPS (virtual private server) - servidor remoto dedicado
- private cloud (nuvem privada) - um datacenter dedicado
- public cloud (nuvens públicas): --- computação elástica (autoscaling)
    - Amazon Web Services (AWS),
    - Google Cloud Platform (GCP),
    - Azure (Microsoft),
    - Openshift
    - Heroku
    - Vercel
    - ...

- Base de usuários crescente
- Tráfego variável
- Ter partes da aplicação com mais uso do que outras partes
- ...

Problema: escala, como a infraestrutura e a aplicação crescem de forma a atender a demanda.

Escalonamento (scale): 2 estratégias
    - Vertical Scaling (escalonamento vertical - scaling up)
    * melhorar o hardware: mais CPU, mais RAM, mais Storage, mais banda, existem casos elásticos (vCPU, vRAM, ...) e que exigem downtime - monolitos (monólitos - monolithic app - um pacote único)
    - Horizontal Scaling (escalonamento horizontal - scaling out)
    * aumentar o número de "máquinas"

Quanto a gente tem uma aplicação, ela tem um _workload_ (carga necessária). Um modo de melhorar a performance, ou, em outras palavras, escalar melhor, é dividir o workload.

_overengineering_ (sobrengenharia)

Em vez de ter um monolito (um único app, pacote) temos vários apps/pacotes. Conhecido como Arquitetura de Microsserviços (_microservices_). transtorno vs ganhos (_tradeoff_).

Vantagens em microsserviços:
- **Escalar diferentes partes da aplicação**
- Implantar diferentes partes da aplicação
- Heterogeneidade: usar tecnologias diferentes para cada parte (linguagens diferentes, bancos diferentes, arquiteturas internas)
- Times diferentes trabalhando em microsserviços diferentes


Desvantagens:
- Arquitetura distribuída (chamadas de métodos passam a ser chamadas de endpoints) -- tudo é remoto -- pode cair rede, fica mais lenta cada requisição.
- Mais difícil de debuggar (distributed tracing - rastreamento distribuído)
- Heterogênea (vantagem e uma desvantagem): tecnologias diferentes em diferentes serviços

Ex.: endpoints (endereços)

"Máquina" só para a consulta de consultas (deploy próprio)
/usuarios/consultas

"Outra máquina"
/dentista/atender
/atendente/marcar

Trechos (endpoints, classes, métodos, etc) com mais volume de uso, são conhecidos como "hotspots", qual endpoint é mais requisitado? Seria /usuarios/consultas, por exemplo. Podemos extrair a consulta dos usuários para uma aplicação separada (microsserviço)

Formação dos devs:
- T-shaped developer, polímata (poliglota), especialista