package info.anyksciaibus.services;

import info.anyksciaibus.entities.timeconstraints.RunsOnYearly;

import java.util.List;

public record DatePropertiesRecord(boolean isPublicHoliday, List<RunsOnYearly> runsOnYearlyList) {
}
