import { CanActivate, ExecutionContext, ForbiddenException, Injectable } from '@nestjs/common';
import { Reflector } from '@nestjs/core';

@Injectable()
export class RolesGuard implements CanActivate {
  constructor(private reflector: Reflector) {}

  canActivate(context: ExecutionContext): boolean {
    const requiredRoles = this.reflector.get<string[]>('roles', context.getHandler()) || [];
    if (!requiredRoles.length) return true;

    const user = context.switchToHttp().getRequest().user;
    const userRoles = user?.roles || [];

    const hasRole = userRoles.some((role: string) => requiredRoles.includes(role));
    if (!hasRole) throw new ForbiddenException('Not enough permissions');
    
    return true;
  }
}
