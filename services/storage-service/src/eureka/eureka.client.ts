import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { Eureka } from 'eureka-js-client';

@Injectable()
export class EurekaClientService {
  private eurekaClient: Eureka;

  constructor(private configService: ConfigService) {
    const hostName = this.configService.get<string>('HOST_EUREKA') || 'localhost';
    const storagePort = parseInt(this.configService.get<string>('STORAGE_PORT') || '3000', 10);

    console.log(`🌍 Eureka host: ${hostName}`);
    console.log(`📦 STORAGE_PORT: ${storagePort}`);

    this.eurekaClient = new Eureka({
      instance: {
        app: 'storage-service',
        instanceId: `storage-service:${storagePort}`,
        hostName: hostName,
        ipAddr: 'storage-service',
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
        host: hostName,
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
