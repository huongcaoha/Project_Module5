package com.ra.module5_project.service.movie;

import com.ra.module5_project.exception.BadRequestException;
import com.ra.module5_project.exception.CustomException;
import com.ra.module5_project.exception.NoResourceFoundException;
import com.ra.module5_project.exception.ResourceNotFoundException;
import com.ra.module5_project.model.dto.movie.MovieDTO;
import com.ra.module5_project.model.dto.movie.MovieUpdateDTO;
import com.ra.module5_project.model.entity.Category;
import com.ra.module5_project.model.entity.Movie;
import com.ra.module5_project.repository.CategoryRepository;
import com.ra.module5_project.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void initializeMovie() {
        if (movieRepository.count() == 0) {
            // 1. Lấy danh sách category từ database để tham chiếu
            List<Category> allCats = categoryRepository.findAll();
            Map<String, Category> catMap = allCats.stream()
                    .collect(Collectors.toMap(Category::getCategoryName, c -> c));

            List<Movie> movies = List.of(
                    Movie.builder()
                            .movieName("Lật Mặt 7: Một Điều Ước")
                            .duration(138)
                            .releaseDate(LocalDate.now())
                            .director("Lý Hải")
                            .cast("Thanh Hiền, Trương Minh Cường, Đinh Y Nhung")
                            .description("Câu chuyện xoay quanh bà Hai và 5 người con. Khi bà Hai gặp tai nạn, liệu ai trong số những người con sẽ về chăm sóc mẹ?")
                            .language("Tiếng Việt")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019832_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Tâm lý - Tình cảm"), catMap.get("Hành động")))
                            .build(),

                    Movie.builder()
                            .movieName("Dune: Hành Tinh Cát - Phần 2")
                            .duration(166)
                            .releaseDate(LocalDate.now())
                            .director("Denis Villeneuve")
                            .cast("Timothée Chalamet, Zendaya, Rebecca Ferguson")
                            .description("Paul Atreides hợp lực với Chani và người Fremen để trả thù những kẻ đã hủy diệt gia đình mình.")
                            .language("Tiếng Anh")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019807_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Viễn tưởng (Sci-Fi)"), catMap.get("Phiêu lưu")))
                            .build(),

                    Movie.builder()
                            .movieName("Exhuma: Quật Mộ Trùng Tang")
                            .duration(134)
                            .releaseDate(LocalDate.now())
                            .director("Jang Jae-hyun")
                            .cast("Choi Min-sik, Kim Go-eun, Yoo Hae-jin")
                            .description("Hai pháp sư, một thầy phong thủy và một chuyên gia khâm liệm cùng hợp sức để giải mã những sự kiện kỳ quái liên quan đến một ngôi mộ cổ.")
                            .language("Tiếng Hàn")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019956_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Kinh dị"), catMap.get("Thần thoại - Kỳ ảo")))
                            .build(),

                    Movie.builder()
                            .movieName("Mai")
                            .duration(131)
                            .releaseDate(LocalDate.now())
                            .director("Trấn Thành")
                            .cast("Phương Anh Đào, Tuấn Trần, Hồng Đào")
                            .description("Mai là một phụ nữ gần 40 tuổi, làm nghề massage. Cuộc đời cô thay đổi khi gặp Dương, một chàng trai trẻ tuổi hào hoa.")
                            .language("Tiếng Việt")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019831_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Tâm lý - Tình cảm")))
                            .build(),

                    Movie.builder()
                            .movieName("Kung Fu Panda 4")
                            .duration(94)
                            .releaseDate(LocalDate.now())
                            .director("Mike Mitchell")
                            .cast("Jack Black, Awkwafina, Viola Davis")
                            .description("Sau khi trở thành Thần Long Đại Hiệp, Po được chọn để trở thành Thủ lĩnh tâm linh của Thung lũng Bình Yên.")
                            .language("Tiếng Anh")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019918_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Hoạt hình"), catMap.get("Hài hước")))
                            .build(),

                    Movie.builder()
                            .movieName("Godzilla x Kong: Đế Chế Mới")
                            .duration(113)
                            .releaseDate(LocalDate.now())
                            .director("Adam Wingard")
                            .cast("Rebecca Hall, Brian Tyree Henry")
                            .description("Hai thực thể siêu nhiên Godzilla và Kong cùng hợp sức chống lại một mối đe dọa mới khủng khiếp từ lòng đất.")
                            .language("Tiếng Anh")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019894_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Hành động"), catMap.get("Viễn tưởng (Sci-Fi)")))
                            .build(),

                    Movie.builder()
                            .movieName("Inside Out 2")
                            .duration(96)
                            .releaseDate(LocalDate.now())
                            .director("Kelsey Mann")
                            .cast("Amy Poehler, Maya Hawke, Phyllis Smith")
                            .description("Riley chính thức bước vào tuổi dậy thì, và các cảm xúc mới như Lo Âu, Ganh Tị, Chán Nản xuất hiện.")
                            .language("Tiếng Anh")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019898_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Hoạt hình"), catMap.get("Hài hước"), catMap.get("Tâm lý - Tình cảm")))
                            .build(),

                    Movie.builder()
                            .movieName("Thám Tử Lừng Danh Conan: Ngôi Sao Năm Cánh 1 Triệu Đô")
                            .duration(110)
                            .releaseDate(LocalDate.now())
                            .director("Chika Nagaoka")
                            .cast("Minami Takayama, Kappei Yamaguchi")
                            .description("Conan và Heiji cùng nhau giải mã những bí ẩn liên quan đến một thanh kiếm cổ và siêu đạo chích Kaito Kid.")
                            .language("Tiếng Nhật")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019879_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Hoạt hình"), catMap.get("Trinh thám - Hình sự")))
                            .build(),

                    Movie.builder()
                            .movieName("Deadpool & Wolverine")
                            .duration(127)
                            .releaseDate(LocalDate.now())
                            .director("Shawn Levy")
                            .cast("Ryan Reynolds, Hugh Jackman")
                            .description("Deadpool phải tìm kiếm sự giúp đỡ từ một phiên bản Wolverine khác để cứu lấy vũ trụ của mình khỏi sự diệt vong.")
                            .language("Tiếng Anh")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019870_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Hành động"), catMap.get("Hài hước"), catMap.get("Viễn tưởng (Sci-Fi)")))
                            .build(),

                    Movie.builder()
                            .movieName("Kẻ Ẩn Danh")
                            .duration(110)
                            .releaseDate(LocalDate.now())
                            .director("Dan Trần")
                            .cast("Kiều Minh Tuấn, Mạc Văn Khoa, Quốc Trường")
                            .description("Lâm là một võ sư có quá khứ bí ẩn, đang sống yên bình thì gia đình anh bị cuốn vào một âm mưu nguy hiểm.")
                            .language("Tiếng Việt")
                            .poster("https://chieuphimquocgia.com.vn/_next/image?url=https%3A%2F%2Fapiv2.chieuphimquocgia.com.vn%2FContent%2FImages%2F0019899_0.jpg&w=256&q=75")
                            .categories(Set.of(catMap.get("Hành động"), catMap.get("Trinh thám - Hình sự")))
                            .build()
            );

            movieRepository.saveAll(movies);
            System.out.println(">>> Đã khởi tạo 10 bộ phim với ĐẦY ĐỦ thuộc tính thành công!");
        }
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {

        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies;
    }

    //Thêm mới
    @Override
    public Movie create(MovieDTO movieDTO) throws CustomException {

        // Tạo mới Movie
        Movie movie = new Movie();
        movie.setMovieName(movieDTO.getMovieName());
        movie.setDuration(movieDTO.getDuration());
        if(movieDTO.getReleaseDate().isBefore(LocalDate.now())) {
            throw new CustomException("Ngày phát hành phải lớn hơn hoặc bằng ngày hiện tại");
        }
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDirector(movieDTO.getDirector());
        movie.setCast(movieDTO.getCast());
        movie.setDescription(movieDTO.getDescription());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setPoster(movieDTO.getPoster());

        // Lấy danh sách các ID thể loại từ MovieDTO
        Set<Long> categoryIds = movieDTO.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<Category> categories = new HashSet<>();

            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                if (category == null) {
                    throw new ResourceNotFoundException("Danh mục không tồn tại.");
                }
                categories.add(category); // Thêm thể loại vào Set
            }

            movie.setCategories(categories); // Cập nhật bộ sưu tập thể loại cho phim
        }


        // Lưu đối tượng movie vào cơ sở dữ liệu
        movie = movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie findById(Long id) throws NoResourceFoundException {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            throw new NoResourceFoundException("Bộ phim không tồn tại");
        }
        return movie;
    }

    @Override
    public Movie update(MovieUpdateDTO movieUpdateDTO, Long id) throws BadRequestException {
        Movie movie = findById(id);
        movie.setId(id);
        // Kiểm tra trùng tên phim trước khi tiếp tục
        boolean check = movieRepository.existsByMovieNameAndId(movieUpdateDTO.getMovieName(), id);
        if (check) {
            throw new BadRequestException("Tên phim đã tồn tại");
        }

        // Cập nhật các thuộc tính của phim
        movie.setMovieName(movieUpdateDTO.getMovieName());
        movie.setDuration(movieUpdateDTO.getDuration());
        movie.setDirector(movieUpdateDTO.getDirector());
        movie.setCast(movieUpdateDTO.getCast());
        movie.setDescription(movieUpdateDTO.getDescription());
        movie.setLanguage(movieUpdateDTO.getLanguage());
        movie.setReleaseDate(movieUpdateDTO.getReleaseDate());
        movie.setPoster(movieUpdateDTO.getPoster());

        // Lấy danh sách các ID thể loại từ MovieDTO
        Set<Long> categoryIds = movieUpdateDTO.getCategoryIds();
        if (categoryIds != null && !categoryIds.isEmpty()) {
            Set<Category> categories = new HashSet<>();

            for (Long categoryId : categoryIds) {
                Category category = categoryRepository.findById(categoryId).orElse(null);

                if (category == null) {
                    throw new ResourceNotFoundException("Danh mục không tồn tại.");
                }

                categories.add(category); // Thêm thể loại vào Set
            }

            movie.setCategories(categories); // Cập nhật bộ sưu tập thể loại cho phim
        }

        // Lưu lại thông tin phim đã cập nhật
        movie = movieRepository.save(movie);
        return movie;
    }


    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    @Override
    public Page<Movie> search(String keyword, Pageable pageable) {
        Page<Movie> movies = movieRepository.findByMovieNameContaining(keyword, pageable);
        return movies;
    }

    //Phim dang chieu
    @Override
    public List<Movie> findAllMoviesLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        return movieRepository.findMoviesFromLast7Days(startDate, endDate);
    }

    //Phim sap chieu
    @Override
    public List<Movie> findAllMoviesInFuture() {
        LocalDate currentDate = LocalDate.now();  // Ngày hiện tại
        return movieRepository.findMoviesInFuture(currentDate);
    }

    //Phim chieu trong ngay hien tai
    @Override
    public List<Movie> findAllMoviesNews() {
        LocalDate currentDate = LocalDate.now();  // Ngày hiện tại
        return movieRepository.findMoviesNew(currentDate);
    }

    @Override
    public List<Movie> getMovieByMonth() {
        LocalDate currentDate = LocalDate.now();
        List<Movie> movies = movieRepository.getMovieByMonth(currentDate);
        return movies.stream().filter((movie) -> {
            LocalDate endDate = movie.getReleaseDate().plusDays(30);
            return !endDate.isBefore(currentDate) ;
        }).toList();
    }



}
