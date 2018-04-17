package com.example.demo.repository;

import com.example.demo.entity.Article;
import com.example.demo.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

   /* @Query(value = " SELECT " +
            " t1.id AS articleId, " +
            " t1.title AS articleTitle, " +
            " t1.content AS articleContent, " +
            " users.name AS authorName, " +
            " categories.id AS categoryId, " +
            " categories.name AS categoryName " +
            " FROM articles t1 " +
            " INNER JOIN categories ON categories.id = t1.category_id " +
            " INNER JOIN users ON users.id = t1.author_id " +
            " GROUP BY t1.title,t1.content,categories.name,users.name " +
            " LIMIT :limit OFFSET :offset ",nativeQuery = true)
    List<Page> getArticleForPage(@Param("limit") Integer limit, @Param("offset") Integer offset);*/

    Page<Article> findAllByCategoryOrderByIdDesc(Category category, Pageable pageable);
}
