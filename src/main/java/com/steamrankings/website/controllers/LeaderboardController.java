package com.steamrankings.website.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.steamrankings.service.api.leaderboards.Leaderboards;
import com.steamrankings.service.api.leaderboards.RankEntryByAchievements;

@Controller
public class LeaderboardController {

    /*
     * Offline Test Data - Do not delete
     * @ModelAttribute("rankentries") public List<RankEntryByAchievements>
     * populateTable() {
     * 
     * RankEntryByAchievements r1 = new RankEntryByAchievements(1, 1234, "swag",
     * 500, "50", "Canada"); RankEntryByAchievements r2 = new
     * RankEntryByAchievements(2, 4321, "yolo", 501, "51", "USA");
     * List<RankEntryByAchievements> curList = new
     * ArrayList<RankEntryByAchievements>(); curList.add(r1); curList.add(r2);
     * 
     * return curList; }
     */

    @RequestMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        List<RankEntryByAchievements> rankEntries = Leaderboards.getRanksByAchievementTotal(1, 10);
        for (int i = 0; i < rankEntries.size(); i++) {
            if (rankEntries.get(i).getCountryCode() != null && !rankEntries.get(i).getCountryCode().equals("")) {
                String countryFlag = "/assets/images/country_flags/" + rankEntries.get(i).getCountryCode().toLowerCase() + ".png";
                rankEntries.set(i, new RankEntryByAchievements(rankEntries.get(i).getRankNumber(), rankEntries.get(i).getId64(), rankEntries.get(i).getName(), rankEntries.get(i)
                        .getAchievementsTotal(), rankEntries.get(i).getCompletionRate(), countryFlag));
            } else {
                String countryFlag = "/assets/images/country_flags/_United Nations.png";
                rankEntries.set(i, new RankEntryByAchievements(rankEntries.get(i).getRankNumber(), rankEntries.get(i).getId64(), rankEntries.get(i).getName(), rankEntries.get(i)
                        .getAchievementsTotal(), rankEntries.get(i).getCompletionRate(), countryFlag));
            }
        }
        model.addAttribute("rankentries", rankEntries);
        return "leaderboard";
    }
}
