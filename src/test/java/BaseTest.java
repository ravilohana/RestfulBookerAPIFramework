import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import util.TestDataHelper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class BaseTest {


    protected final Faker faker = TestDataHelper.getFaker();

    // Data provider
    // With for loop
    @DataProvider(name = "bookingDataWithForLoop" ,parallel = true)
    public Object[][] bookingDataProviderWithLoop(){
        var faker =  TestDataHelper.getFaker();
        var name = faker.name();
        var dateFormatter =  DateTimeFormatter.ISO_DATE;
        var numberOfPlusDays = TestDataHelper.getRandomInt(2);

        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            Object[] objects = new Object[]{name.firstName(), name.lastName(), faker.number().randomNumber(3, true)
                    , faker.bool().bool(), faker.food().dish(), TestDataHelper.getFutureDate(numberOfPlusDays, dateFormatter)
                    , TestDataHelper.getFutureDate(numberOfPlusDays+4, dateFormatter)};
            list.add(objects);
        }
        return list.toArray(new Object[0][]);
    }



    // Data provider
    // With Stream
    @DataProvider(name = "bookingDataWithStream",parallel = true)
    public Object[][] bookingDataProviderWithStream() {
        var faker = this.faker;
        var name = faker.name();
        var dateFormatter = DateTimeFormatter.ISO_DATE;

        return IntStream.range(0, 5)
                        .mapToObj(i -> {

                            var numberOfPlusDays = TestDataHelper.getRandomInt(2);
                            return new Object[]{name.firstName(), name.lastName(), faker.number().randomNumber(3, true)
                                    , faker.bool().bool(), faker.food().dish(), TestDataHelper.getFutureDate(numberOfPlusDays, dateFormatter)
                                    , TestDataHelper.getFutureDate(numberOfPlusDays+4, dateFormatter)};
                        })
                        .toArray(Object[][]::new);
    }
}
