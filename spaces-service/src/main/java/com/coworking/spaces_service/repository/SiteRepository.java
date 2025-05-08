package com.coworking.spaces_service.repository;

import com.coworking.spaces_service.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site,Long> {
    Optional<Site> findByNameAndAddressAndCityAndDistrict(String siteName, String siteAddress, String siteCity, String siteDistrict);
}
