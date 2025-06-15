import { Controller, Post, UploadedFile, UseInterceptors, UseGuards } from '@nestjs/common';
import { FileInterceptor } from '@nestjs/platform-express';
import { CloudinaryService } from './cloudinary.service';
import { ROLE } from 'src/util/role.enum';
import { Roles } from 'src/config/authentication/roles.decorator';
import { JwtAuthGuard } from 'src/config/authentication/jwt-auth.guard';
import { RolesGuard } from 'src/config/authentication/roles.guard';
//swagger ...
@UseGuards(JwtAuthGuard, RolesGuard)
@Controller('upload')
export class CloudinaryController {
  constructor(private readonly cloudinaryService: CloudinaryService) {}

  @Post('user')
  @Roles(ROLE.USER)
  @UseInterceptors(FileInterceptor('file'))
  async uploadFile(@UploadedFile() file: Express.Multer.File): Promise<string> {
    return await this.cloudinaryService.uploadImageToUserFolder(file);
  }

  @Post('spaces')
  @Roles(ROLE.ADMIN) 
  @UseInterceptors(FileInterceptor('file'))
  async uploadFileSpaces(@UploadedFile() file: Express.Multer.File): Promise<string> {
    return await this.cloudinaryService.uploadImageToSpacesFolder(file);
  }
}
