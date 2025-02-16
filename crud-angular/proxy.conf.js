//Criei esse arquivo devido ao erro de CORS que é chamadas em dominios diferentes, o que ele faz, quando chamar /api vai alterar de padrão (http://localhost:4200/) para http://localhost:8080/api
// tem que configurar no arquivo package.json a linha, exemplo: "start": "ng serve --proxy-config proxy.conf.js",
const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://localhost:8080/',
    secure: false,
    logLevel: 'debug'
  }
];

module.exports = PROXY_CONFIG;
