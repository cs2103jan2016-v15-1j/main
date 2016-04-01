package cs2103.v15_1j.jimjim.parser;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.SearchCommand;
import cs2103.v15_1j.jimjim.searcher.DateTimeFilter;
import cs2103.v15_1j.jimjim.searcher.Filter;
import cs2103.v15_1j.jimjim.searcher.OverdueFilter;
import cs2103.v15_1j.jimjim.searcher.TimeFilter;

public class JJParserSearchFilterTest {
		
	JJParser parser;

	@Before
	public void setUp() throws Exception {
		this.parser = new JJParser(null);
	}

    @Test
    public void testTimeOnly() {
        Command result = this.parser.parse("search at 7pm");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof TimeFilter);
        TimeFilter castedFilter = (TimeFilter) filter;
        assertEquals(LocalTime.of(18, 30), castedFilter.getStart());
        assertEquals(LocalTime.of(19, 30), castedFilter.getEnd());
    }
    
    @Test
    public void testTime() {
        Command result = this.parser.parse("search before 7pm");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof TimeFilter);
        TimeFilter castedFilter = (TimeFilter) filter;
        assertEquals(LocalTime.MIN, castedFilter.getStart());
        assertEquals(LocalTime.of(19, 0), castedFilter.getEnd());

        result = this.parser.parse("search after 19.30");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof TimeFilter);
        castedFilter = (TimeFilter) filter;
        assertEquals(LocalTime.of(19, 30), castedFilter.getStart());
        assertEquals(LocalTime.MAX, castedFilter.getEnd());

        result = this.parser.parse("search between 7pm and 7.30pm");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof TimeFilter);
        castedFilter = (TimeFilter) filter;
        assertEquals(LocalTime.of(19, 0), castedFilter.getStart());
        assertEquals(LocalTime.of(19, 30), castedFilter.getEnd());
    }

    @Test
    public void testDateOnly() {
        Command result = this.parser.parse("search 5th April 2016");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        DateTimeFilter castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.MAX),
                castedFilter.getEnd());
    }
    
    @Test
    public void testDate() {
        Command result = this.parser.parse("search after 5th April 2016");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        DateTimeFilter castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(LocalDateTime.MAX, castedFilter.getEnd());

        result = this.parser.parse("search before 5-4-2016");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.MAX),
                castedFilter.getEnd());
        assertEquals(LocalDateTime.MIN, castedFilter.getStart());

        result = this.parser.parse("search between 5-4-2016 and 6-5-2016");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 5, 6), LocalTime.MAX),
                castedFilter.getEnd());
    }

    @Test
    public void testDateTime() {
        Command result = this.parser.parse("search after 2pm 5th April 2016");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        DateTimeFilter castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.of(14, 0)),
                castedFilter.getStart());
        assertEquals(LocalDateTime.MAX, castedFilter.getEnd());

        result = this.parser.parse("search before 5-4-2016 10");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.of(10, 0)),
                castedFilter.getEnd());
        assertEquals(LocalDateTime.MIN, castedFilter.getStart());

        result = this.parser.parse("search between 5-4-2016 11am and 6-5-2016 5.30pm");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        castedFilter = (DateTimeFilter) filter;
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 4, 5), LocalTime.of(11, 0)),
                castedFilter.getStart());
        assertEquals(LocalDateTime.of(LocalDate.of(2016, 5, 6), LocalTime.of(17, 30)),
                castedFilter.getEnd());
    }

    @Test
    public void testWeek() {
        Command result = this.parser.parse("search this week");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        DateTimeFilter castedFilter = (DateTimeFilter) filter;
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.with(DayOfWeek.MONDAY).with(LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(now.with(DayOfWeek.SUNDAY).with(LocalTime.MAX),
                castedFilter.getEnd());

        result = this.parser.parse("search next week");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        castedFilter = (DateTimeFilter) filter;
        assertEquals(now.plusWeeks(1).with(DayOfWeek.MONDAY).with(LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(now.plusWeeks(1).with(DayOfWeek.SUNDAY).with(LocalTime.MAX),
                castedFilter.getEnd());
    }

    @Test
    public void testMonth() {
        Command result = this.parser.parse("search this month");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        DateTimeFilter castedFilter = (DateTimeFilter) filter;
        LocalDateTime now = LocalDateTime.now();
        assertEquals(now.withDayOfMonth(1).with(LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(
                now.withDayOfMonth(1).plusMonths(1).minusDays(1).with(LocalTime.MAX),
                castedFilter.getEnd());

        result = this.parser.parse("search next month");
        assertTrue(result instanceof SearchCommand);
        casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        filter = casted.getFilters().get(0);
        assertTrue(filter instanceof DateTimeFilter);
        castedFilter = (DateTimeFilter) filter;
        assertEquals(now.plusMonths(1).withDayOfMonth(1).with(LocalTime.MIN),
                castedFilter.getStart());
        assertEquals(
                now.withDayOfMonth(1).plusMonths(2).minusDays(1).with(LocalTime.MAX),
                castedFilter.getEnd());
    }

    @Test
    public void testOverdue() {
        Command result = this.parser.parse("search overdue");
        assertTrue(result instanceof SearchCommand);
        SearchCommand casted = (SearchCommand) result;
        assertEquals(1, casted.getFilters().size());
        Filter filter = casted.getFilters().get(0);
        assertTrue(filter instanceof OverdueFilter);
    }
}
