package ru.netology.repository;



import ru.netology.model.Post;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class PostRepository {
  private final AtomicLong userId;
  private final Map<Long, Post> listofPosts;

  public PostRepository() {
    userId = new AtomicLong(0);
    listofPosts = new ConcurrentHashMap<>();
  }

  public List<Post> all() {
    return new ArrayList<>(listofPosts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(listofPosts.get(id));
  }

  public Post save(Post post) {
    long specificID = post.getId();
    if (specificID > 0 && listofPosts.containsKey(specificID)) {
      listofPosts.replace(specificID, post);
    } else {
      long newIdPost = specificID == 0 ? userId.incrementAndGet() : specificID;
      post.setId(newIdPost);
      listofPosts.put(newIdPost, post);
    }
    return post;
  }

  public void removeById(long id) {
    listofPosts.remove(id);
  }
}
