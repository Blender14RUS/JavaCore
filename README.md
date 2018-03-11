[![My trello](https://s8.hostingkartinok.com/uploads/images/2017/11/210cf3c439f53c23d63a85ccd947b907.png)](https://trello.com/b/e92fDBWo/blender14rus-github)   

***

### JavaCore
First Header | Second Header
------------ | -------------
Archiver.java | zip/unzip functions
CacheLoader.java | simple loader mp3 from google chrome cache
Cmd.java | emulator cmd with basic commands
JavaErrors.java | JavaErrors examples
Spiral.java | spiral prints symmetrical multidimensional arrays

Rectangle.java:   
_area_ - 2 точки прямоугольника (диагональ) и возвращает его площадь (типа: double).   
_intersectionArea_ - 2 прямоугольника (каждый строится с помощью 2х точек) и возвращается площадь области их пересечения (вне зависимости от того в какой [четверти](https://i.imgur.com/CK4G09H.png) находятся прямоугольники)

***

### BookLibrary
H2 DataBase   
- [x] Book getBook(int id)
- [x] List<Book> getBooks()
- [x] boolean addBook(Book book)
- [x] boolean deleteBook(int id)
- [x] boolean updateBook(int id_book, Book book)
- [x] boolean addAuthor(Author author)
- [x] List<Author> getAuthors()
- [x] List<Integer> getAuthors(int id_books)
- [x] List<Book> getBooksOfAuthor(int id_author)
- [x] boolean addBooksOfAuthor(int id_author, int id_book)
- [ ] boolean updateAuthor(int id_author, Author author) (update data in BooksOfAuthor table)
