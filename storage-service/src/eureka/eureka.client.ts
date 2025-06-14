import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { Eureka } from 'eureka-js-client';

@Injectable()
export class EurekaClientService {
  private eurekaClient: Eureka;

  constructor(private configService: ConfigService) {
    const hostName = this.configService.get<string>('HOST_EUREKA') || 'localhost';
    console.log(hostName);
    this.eurekaClient = new Eureka({
      instance: {
        app: 'storage-service',
        hostName: hostName,
        ipAddr: '127.0.0.1',
        port: {
          $: 3000,
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
      },
    });
  }

  public start() {
    this.eurekaClient.start((error) => {
      if (error) {
        console.error('Error al iniciar Eureka:', error);
      } else {
        console.log('🚀 Eureka cliente iniciado correctamente');
      }
    });
  }

  public stop() {
    this.eurekaClient.stop((error) => {
      if (error) {
        console.error('Error al detener Eureka:', error);
      } else {
        console.log('Eureka cliente detenido correctamente');
      }
    });
  }

  public getInstancesByAppId(appId: string) {
    try {
      const instances = this.eurekaClient.getInstancesByAppId(appId);
      console.log('Instancias obtenidas:', instances);
      return instances;
    } catch (error) {
      console.error('Error al obtener instancias por AppId:', error);
      return null;
    }
  }

  public getInstancesByVipAddress(vipAddress: string) {
    try {
      const instances = this.eurekaClient.getInstancesByVipAddress(vipAddress);
      console.log('Instancias obtenidas por VIP:', instances);
      return instances;
    } catch (error) {
      console.error('Error al obtener instancias por VIP:', error);
      return null;
    }
  }
}
