import { Module } from '@nestjs/common';
import { EurekaClientService } from './eureka.client';
import { ConfigModule } from '@nestjs/config';
@Module({
  imports: [ConfigModule],
  providers: [EurekaClientService],
  exports: [EurekaClientService],
})
export class EurekaModule {}
