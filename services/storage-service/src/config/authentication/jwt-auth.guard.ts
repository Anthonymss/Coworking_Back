import {
    CanActivate,
    ExecutionContext,
    Injectable,
    UnauthorizedException,
  } from '@nestjs/common';
  import { ConfigService } from '@nestjs/config';
  import { Request } from 'express';
  import * as jwt from 'jsonwebtoken';
  import { JwtPayload } from 'jsonwebtoken';
  
  interface RequestWithUser extends Request {
    user: string | JwtPayload;
  }
  
  @Injectable()
  export class JwtAuthGuard implements CanActivate {
    constructor(private readonly config: ConfigService) {}
  
    canActivate(context: ExecutionContext): boolean {
      const req = context.switchToHttp().getRequest<RequestWithUser>();
      const authHeader = req.headers['authorization'] ?? '';
  
      if (!authHeader.startsWith('Bearer ')) {
        throw new UnauthorizedException('Token not provided');
      }
  
      const token = authHeader.slice(7).trim();
      console.log('Token:', token);
      const secretBase64 = this.config.get<string>('JWT_SECRET');
      if (!secretBase64) {
        throw new UnauthorizedException('JWT_SECRET not configured');
      }
      const secretKey = Buffer.from(secretBase64, 'base64');
  
      try {
        const payload = jwt.verify(token, secretKey, {
          algorithms: ['HS512'],
        });
        req.user = payload as JwtPayload;
        return true;
    } catch (err) {
        if (err instanceof jwt.TokenExpiredError) {
          throw new UnauthorizedException('Token has expired');
        } else if (err instanceof jwt.JsonWebTokenError) {
          throw new UnauthorizedException('Invalid JWT token');
        } else {
          throw new UnauthorizedException('Unauthorized access');
        }
    }
    }
  }
  