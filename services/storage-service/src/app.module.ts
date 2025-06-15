import { Module, OnModuleInit } from '@nestjs/common';

import { CloudinaryModule } from './cloudinary/cloudinary.module';
import { ConfigModule } from '@nestjs/config';
import { EurekaModule } from './eureka/eureka.module';
import { EurekaClientService } from './eureka/eureka.client';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    CloudinaryModule,
    EurekaModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule implements OnModuleInit {
  constructor(private readonly eurekaClientService: EurekaClientService) {}
  onModuleInit() {
    this.eurekaClientService.start();
  }
}
  
