import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { Eureka } from 'eureka-js-client';

@Injectable()
export class EurekaClientService {
  private eurekaClient: Eureka;

  constructor(private configService: ConfigService) {
    const eurekaHost = this.configService.get<string>('HOST_EUREKA') || 'localhost';
    const storagePort = parseInt(this.configService.get<string>('STORAGE_PORT') || '3000', 10);
    const serviceHostName = 'storage-service'; // nombre del contenedor en Docker

    console.log(`🌍 Eureka host: ${eurekaHost}`);
    console.log(`📦 STORAGE_PORT: ${storagePort}`);
    console.log(`🛰️  Registrando storage-service en Eureka con IP: ${serviceHostName} y puerto: ${storagePort}`);

    this.eurekaClient = new Eureka({
      instance: {
        app: 'storage-service',
        instanceId: `storage-service:${storagePort}`,
        hostName: serviceHostName,
        ipAddr: serviceHostName,
        port: {
          $: storagePort,
          '@enabled': true,
        },
        vipAddress: 'storage-service',
        dataCenterInfo: {
          '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
          name: 'MyOwn',
        },
      },
      eureka: {
        host: eurekaHost,
        port: 8761,
        servicePath: '/eureka/apps/',
        maxRetries: 10,
        requestRetryDelay: 2000,
      },
    });
  }

  public start() {
    this.eurekaClient.start((error) => {
      if (error) {
        console.error('❌ Error al iniciar Eureka:', error);
      } else {
        console.log('🚀 Eureka cliente iniciado correctamente');
      }
    });
  }

  public stop() {
    this.eurekaClient.stop((error) => {
      if (error) {
        console.error('❌ Error al detener Eureka:', error);
      } else {
        console.log('🛑 Eureka cliente detenido correctamente');
      }
    });
  }

  public getInstancesByAppId(appId: string) {
    try {
      const instances = this.eurekaClient.getInstancesByAppId(appId);
      console.log('📦 Instancias obtenidas:', instances);
      return instances;
    } catch (error) {
      console.error('❌ Error al obtener instancias por AppId:', error);
      return null;
    }
  }

  public getInstancesByVipAddress(vipAddress: string) {
    try {
      const instances = this.eurekaClient.getInstancesByVipAddress(vipAddress);
      console.log('📡 Instancias obtenidas por VIP:', instances);
      return instances;
    } catch (error) {
      console.error('❌ Error al obtener instancias por VIP:', error);
      return null;
    }
  }
}
