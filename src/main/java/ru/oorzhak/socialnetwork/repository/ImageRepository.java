package ru.oorzhak.socialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oorzhak.socialnetwork.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
