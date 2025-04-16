package com.coworking.spaces_service.persistence.repository;

import com.coworking.spaces_service.persistence.entity.Equipment;
import com.coworking.spaces_service.persistence.entity.Space;
import com.coworking.spaces_service.util.enums.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    @Query("SELECT s FROM Space s WHERE " +
            "(:city IS NULL OR s.site.city = :city) AND " +
            "(:district IS NULL OR s.site.district = :district) AND " +
            "(:spaceType IS NULL OR s.spaceType = :spaceType)")
    List<Space> findSpaces(@Param("city") String city,
                           @Param("district") String district,
                           @Param("spaceType") SpaceType spaceType);
    @Query("SELECT DISTINCT s.site.city FROM Space s")
    List<String> findDistinctCity();

    @Query("SELECT DISTINCT s.site.district FROM Space s")
    List<String> findDistinctDistrict();

}
