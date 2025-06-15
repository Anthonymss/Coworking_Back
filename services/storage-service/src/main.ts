import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import * as morgan from 'morgan';
import { corsConfig } from './config/cors.config';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.use(morgan('dev'));
  app.enableShutdownHooks();
  app.enableCors(corsConfig);
  await app.listen(process.env.STORAGE_PORT ?? 3000);
}
bootstrap();
