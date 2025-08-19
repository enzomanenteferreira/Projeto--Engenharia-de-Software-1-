// URL base para os microserviços locais
const BASE_URL = 'http://localhost:8080/es1backendservicos';

// Endpoint da API de IA (ex.: Gemini 2.0 Flash)
// ATENÇÃO: Expor a chave de API no front end não é recomendado para produção.
const IA_API_ENDPOINT = 'https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyBuH4tlFUtvkV6BCmGaR7FA3bsuyCXP4NA';

// Instrução inicial para o modelo de IA
const INSTRUCAO_INICIAL = `
Você é o assistente virtual do sistema de gerenciamento de receitas médicas de uma clínica.
Seu nome é IA-Receita. Existem vários serviços que o sistema presta:
- Consultar dados de uma receita médica,
- Consultar dados de pacientes,
- Obter bairros,
- Obter cidades,
- Obter endereço por CEP.

**Regras**:
1. Você deve responder apenas em português.
2. Se o usuário perguntar algo fora do contexto de receitas médicas, peça desculpas e explique que não pode responder.
3. Sempre que possível, retorne sua resposta em um formato JSON, contendo:
   {
     "acao": "NOME_DA_ACAO",
     "parametros": { ... }
   }
4. As ações que você pode retornar são:
   - CONSULTAR_RECEITA
   - CONSULTAR_PACIENTE
   - OBTER_BAIRROS
   - OBTER_CIDADE
   - OBTER_ENDERECO_POR_CEP
5. Caso não seja possível identificar uma das ações acima, retorne um JSON indicando erro:
   {
     "raw_output": "Mensagem de erro ou explicação"
   }

**Exemplo de resposta**:
{
  "acao": "CONSULTAR_RECEITA",
  "parametros": {
     "nro_receita": "1234"
  }
}
`;

/* Função para formatar os dados em um layout visual agradável */
function formatData(data) {
  // Se for uma resposta simples de mensagem
  if (data.mensagem && Object.keys(data).length === 1) {
    return `<div class="card mensagem"><p>${data.mensagem}</p></div>`;
  }
  
  // Se houver erro
  if (data.erro || data.error) {
    return `<div class="card error"><h3>Erro</h3><p>${data.erro || data.error}</p></div>`;
  }
  
  // Se for uma receita médica com lista de medicamentos
  if (data.nro_receita && data.medicamentos) {
    let html = `<div class="card receita">
      <h3>Receita Médica Nº ${data.nro_receita}</h3>
      <p><strong>Data da Receita:</strong> ${data.data_receita}</p>
      <p><strong>Paciente (RG):</strong> ${data.rg_paciente}</p>
      <p><strong>Médico (ID):</strong> ${data.id_medico} <span>(${data.crm_medico || 'Sem CRM'})</span></p>
      <p><strong>CID:</strong> ${data.cod_cid} - ${data.nome_cid}</p>
      <h4>Medicamentos</h4>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Data Início</th>
            <th>Data Fim</th>
            <th>Posologia</th>
          </tr>
        </thead>
        <tbody>`;
    data.medicamentos.forEach(m => {
      html += `<tr>
          <td>${m.id_medicamento}</td>
          <td>${m.nome_medicamento}</td>
          <td>${m.data_inicio}</td>
          <td>${m.data_fim}</td>
          <td>${m.posologia}</td>
        </tr>`;
    });
    html += `</tbody></table></div>`;
    return html;
  }
  
  // Se a resposta contiver uma lista de pacientes
  if (data.pacientes && Array.isArray(data.pacientes)) {
    let html = `<div class="card pacientes">
      <h3>Lista de Pacientes</h3>
      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th>RG</th>
          </tr>
        </thead>
        <tbody>`;
    data.pacientes.forEach(p => {
      html += `<tr>
          <td>${p.nome || p.nome_paciente || "N/A"}</td>
          <td>${p.cpf || "N/A"}</td>
          <td>${p.rg || "N/A"}</td>
        </tr>`;
    });
    html += `</tbody></table></div>`;
    return html;
  }
  
  // Caso a resposta contenha outros arrays, você pode adicionar condições semelhantes aqui
  
  // Fallback: Exibe os dados em lista de chave/valor
  let html = `<div class="card">`;
  for (let key in data) {
    let value = data[key];
    // Se o valor for um array, converte cada item em JSON formatado
    if (Array.isArray(value)) {
      value = value.map(item => typeof item === 'object' ? JSON.stringify(item) : item).join(', ');
    }
    html += `<p><strong>${key}:</strong> ${value}</p>`;
  }
  html += `</div>`;
  return html;
}

/* Função para exibir os dados na área de output */
function displayOutput(data) {
  const outputDiv = document.getElementById('output');
  outputDiv.innerHTML = formatData(data);
}

/* Modal do Chat */
const openChat = document.getElementById('openChat');
const chatModal = document.getElementById('chatModal');
const closeModal = document.querySelector('.close');

openChat.addEventListener('click', () => {
  chatModal.style.display = 'block';
});

closeModal.addEventListener('click', () => {
  chatModal.style.display = 'none';
});

window.addEventListener('click', (event) => {
  if (event.target === chatModal) {
    chatModal.style.display = 'none';
  }
});

/* Função para adicionar mensagens no chat */
function appendMessage(author, messageHTML) {
  const chatOutput = document.getElementById('chatOutput');
  const messageDiv = document.createElement('div');
  messageDiv.classList.add('chat-message');
  messageDiv.innerHTML = `<p><strong>${author}:</strong></p>${messageHTML}`;
  chatOutput.appendChild(messageDiv);
  chatOutput.scrollTop = chatOutput.scrollHeight;
}

/* Função que chama a API de IA (Gemini 2.0) diretamente do front end */
async function chamarServicoIAJS(textoUsuario) {
  const promptCompleto = INSTRUCAO_INICIAL +
    "\n\n[Pergunta do usuário]: " + textoUsuario +
    "\n\n[Responda em JSON conforme instruções acima]:\n";
    
  const payload = {
    contents: [
      {
        parts: [
          { text: promptCompleto }
        ]
      }
    ],
    generationConfig: { temperature: 0.0 }
  };

  const response = await fetch(IA_API_ENDPOINT, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload)
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error("Erro na chamada ao serviço de IA: " + errorText);
  }
  
  const responseJson = await response.json();
  const candidates = responseJson.candidates;
  if (candidates && candidates.length > 0) {
    const partsResponse = candidates[0].content.parts;
    if (partsResponse && partsResponse.length > 0) {
      let content = partsResponse[0].text;
      content = content.replace(/```[a-zA-Z]*/g, '').replace(/```/g, '').trim();
      try {
        return JSON.parse(content);
      } catch (e) {
        return { raw_output: content };
      }
    }
  }
  throw new Error("Resposta da IA não contém 'candidates' ou está vazia: " + JSON.stringify(responseJson));
}

/* Função para chamar microserviços locais via GET */
async function chamarMicroservicoGET(endpoint, nomeParam, valorParam) {
  const url = endpoint + "?" + encodeURIComponent(nomeParam) + "=" + encodeURIComponent(valorParam);
  const response = await fetch(url);
  if (!response.ok) {
    const errorText = await response.text();
    throw new Error("Erro na chamada ao microserviço: " + errorText);
  }
  return await response.json();
}

/* Processamento do envio da mensagem para a IA via front end */
document.getElementById('form-chat-ia').addEventListener('submit', async (e) => {
  e.preventDefault();
  const textoUsuario = document.getElementById('textoUsuario').value;
  appendMessage('Você', `<div class="card"><p>${textoUsuario}</p></div>`);
  try {
    const iaResponse = await chamarServicoIAJS(textoUsuario);
    let resultadoFinal = {};

    if (!iaResponse.acao) {
      // Se não houver ação, exibe o raw_output ou uma mensagem padrão
      resultadoFinal = { mensagem: iaResponse.raw_output || "Resposta não estruturada." };
    } else {
      switch (iaResponse.acao.toUpperCase()) {
        case 'CONSULTAR_RECEITA': {
          const nroReceita = iaResponse.parametros?.nro_receita?.trim();
          if (!nroReceita) {
            resultadoFinal = { faltaparametro: "Parâmetro 'nro_receita' não informado. Por favor, informe o número da receita médica." };
          } else {
            resultadoFinal = await chamarMicroservicoGET(`${BASE_URL}/consultar-receita`, "nro_receita", nroReceita);
          }
          break;
        }
        case 'CONSULTAR_PACIENTE': {
          const rg = iaResponse.parametros?.rg?.trim();
          resultadoFinal = await chamarMicroservicoGET(`${BASE_URL}/obter-pacientes`, "rg", rg);
          break;
        }
        case 'OBTER_BAIRROS':
          resultadoFinal = await chamarMicroservicoGET(`${BASE_URL}/obter-bairros`, "id", iaResponse.parametros?.id || "");
          break;
        case 'OBTER_CIDADE':
          resultadoFinal = await chamarMicroservicoGET(`${BASE_URL}/obter-cidade`, "id", iaResponse.parametros?.id || "");
          break;
        case 'OBTER_ENDERECO_POR_CEP': {
          const cep = iaResponse.parametros?.cep?.trim();
          if (!cep) {
            resultadoFinal = { faltaparametro: "Parâmetro 'cep' não informado. Por favor, informe o CEP." };
          } else {
            resultadoFinal = await chamarMicroservicoGET(`${BASE_URL}/obter-endereco-por-cep`, "cep", cep);
          }
          break;
        }
        default:
          resultadoFinal = { erro: "Ação não reconhecida ou não suportada: " + iaResponse.acao };
      }
    }
    appendMessage('IA', formatData(resultadoFinal));
    document.getElementById('textoUsuario').value = '';
  } catch (err) {
    appendMessage('Erro', `<div class="card error"><p>${err.toString()}</p></div>`);
  }
});

/* --- Outras chamadas dos formulários para os microserviços --- */
// Cadastro de Endereço
document.getElementById('form-cadastrar-endereco').addEventListener('submit', function(e) {
  e.preventDefault();
  const data = {
    cep: document.getElementById('cep').value,
    id_cidade: parseInt(document.getElementById('id_cidade').value),
    id_bairro: parseInt(document.getElementById('id_bairro').value),
    id_logradouro: parseInt(document.getElementById('id_logradouro').value)
  };

  fetch(`${BASE_URL}/cadastrar-endereco`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  })
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Cadastro de Paciente
document.getElementById('form-cadastrar-paciente').addEventListener('submit', function(e) {
  e.preventDefault();
  const data = {
    nome_paciente: document.getElementById('nome_paciente').value,
    cpf: document.getElementById('cpf').value,
    rg: document.getElementById('rg').value,
    numero_endereco: document.getElementById('numero_endereco').value,
    complemento_endereco: document.getElementById('complemento_endereco').value,
    data_nascimento_paciente: document.getElementById('data_nascimento_paciente').value,
    cep: document.getElementById('cep_paciente').value
  };

  fetch(`${BASE_URL}/cadastrar-paciente`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  })
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Cadastro de Receita
document.getElementById('form-cadastrar-receita').addEventListener('submit', function(e) {
  e.preventDefault();
  const medicamento = {
    id_medicamento: parseInt(document.getElementById('id_medicamento').value),
    data_inicio: document.getElementById('data_inicio').value,
    data_fim: document.getElementById('data_fim').value,
    posologia: document.getElementById('posologia').value
  };
  const data = {
    rg_paciente: document.getElementById('rg_paciente').value,
    id_medico: parseInt(document.getElementById('id_medico').value),
    data_receita: document.getElementById('data_receita').value,
    cod_cid: parseInt(document.getElementById('cod_cid').value),
    medicamentos: [medicamento]
  };

  fetch(`${BASE_URL}/cadastrar-receita`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  })
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Consulta de Receita
document.getElementById('form-consultar-receita').addEventListener('submit', function(e) {
  e.preventDefault();
  const nro = document.getElementById('nro_receita').value;
  fetch(`${BASE_URL}/consultar-receita?nro_receita=` + encodeURIComponent(nro))
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Bairros
document.getElementById('form-obter-bairros').addEventListener('submit', function(e) {
  e.preventDefault();
  const id = document.getElementById('id_bairro_consulta').value;
  let url = `${BASE_URL}/obter-bairros`;
  if (id) {
    url += '?id=' + encodeURIComponent(id);
  }
  fetch(url)
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Cidade
document.getElementById('form-obter-cidade').addEventListener('submit', function(e) {
  e.preventDefault();
  const id = document.getElementById('id_cidade_consulta').value;
  let url = `${BASE_URL}/obter-cidade`;
  if (id) {
    url += '?id=' + encodeURIComponent(id);
  }
  fetch(url)
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Endereço Externo
document.getElementById('form-obter-endereco').addEventListener('submit', function(e) {
  e.preventDefault();
  const cep = document.getElementById('cep_endereco_externo').value;
  fetch(`${BASE_URL}/obter-endereco?cep=` + encodeURIComponent(cep))
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Endereço por CEP
document.getElementById('form-obter-endereco-por-cep').addEventListener('submit', function(e) {
  e.preventDefault();
  const cep = document.getElementById('cep_consulta').value;
  fetch(`${BASE_URL}/obter-endereco-por-cep?cep=` + encodeURIComponent(cep))
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Endereço por ID
document.getElementById('form-obter-endereco-por-id').addEventListener('submit', function(e) {
  e.preventDefault();
  const id = document.getElementById('endereco_id').value;
  fetch(`${BASE_URL}/obter-endereco-por-id?id=` + encodeURIComponent(id))
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Logradouros
document.getElementById('form-obter-logradouros').addEventListener('submit', function(e) {
  e.preventDefault();
  fetch(`${BASE_URL}/obter-logradouros`)
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});

// Obter Pacientes
document.getElementById('form-obter-pacientes').addEventListener('submit', function(e) {
  e.preventDefault();
  fetch(`${BASE_URL}/obter-pacientes`)
    .then(res => res.json())
    .then(json => displayOutput(json))
    .catch(err => displayOutput({ error: err.toString() }));
});
