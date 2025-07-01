export const corsConfig = {
    origin: [
      'http://localhost:5000',
      'https://coworking-beta.vercel.app',
    ],
    methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
    preflightContinue: false,
    optionsSuccessStatus: 204,
  };
  