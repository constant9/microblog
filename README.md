# microblog
A microblog model with crud and top lists.
Included a Dockerfile to create a mysql image initiated with project DB schema.

REST apis:
USERs:
POST http://host:8080/microblog/users/{userName}/upvote/{postId}
	upvote an existing post by user. 
  On success returns status ACCEPTED 202.

POST http://host:8080/microblog/users/{userName}/upvote/{postId}
	downvote an existing post by user. 
 On success returns status ACCEPTED 202. 

POST http://host:8080/microblog/users/{userName}
  creates a user with given unique name and returns the user object.
  On duplicate name returns status CONFLICT

GET http://host:8080/microblog/users/{userName}
  retrieve user data.

POSTs:
POST http://host:8080/microblog/posts/
    create a new post.
    req. body example 
    	{
        "subject":"sss",
        "text":"ttt",
        "user_name":"user1"
      }

PUT http://host:8080/microblog/posts/{postId}
  updates the post with id=postId.
  req. body example 
    	{
        "subject":"sss",
        "text":"ttt"       
      }      
    
GET http://host:8080/microblog/posts/{postId}
  retrieves the post.
  resp example:
  { 
    "subject":"subject_305488860",
    "text":"text305488860",
    "id":861,    
    "upvotes":0,
    "creation_date":"03-02-2018 08:40:53",
    "update_date":"03-02-2018 08:40:53"
  }
  on not found returns status NOT_FOUND 404

GET http://host:8080/microblog/posts/top
  retrieves the top N posts (N is configurable).
  the rating is given by following formula:
  sum(upvotes)/total_minutes_since_created + sum(upvotes_since_last_update)/total_minutes_last_update
  


 
