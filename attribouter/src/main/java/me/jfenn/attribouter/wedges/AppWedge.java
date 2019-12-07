package me.jfenn.attribouter.wedges;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.jfenn.attribouter.R;
import me.jfenn.attribouter.adapters.InfoAdapter;
import me.jfenn.attribouter.data.github.GitHubData;
import me.jfenn.attribouter.data.github.RepositoryData;
import me.jfenn.attribouter.wedges.link.GitHubLinkWedge;
import me.jfenn.attribouter.wedges.link.LinkWedge;
import me.jfenn.attribouter.wedges.link.PlayStoreLinkWedge;
import me.jfenn.attribouter.wedges.link.WebsiteLinkWedge;
import me.jfenn.attribouter.utils.ResourceUtils;

public class AppWedge extends Wedge<AppWedge.ViewHolder> {

    @Nullable
    private String icon;
    @Nullable
    private String description;
    @Nullable
    private String playStoreUrl;
    @Nullable
    private String websiteUrl;
    @Nullable
    private String gitHubUrl;

    public AppWedge(XmlResourceParser parser) throws IOException, XmlPullParserException {
        super(R.layout.item_attribouter_app_info);
        icon = parser.getAttributeValue(null, "icon");
        description = parser.getAttributeValue(null, "description");
        playStoreUrl = parser.getAttributeValue(null, "playStoreUrl");
        websiteUrl = parser.getAttributeValue(null, "websiteUrl");
        gitHubUrl = parser.getAttributeValue(null, "gitHubUrl");

        String repo = parser.getAttributeValue(null, "repo");
        if (gitHubUrl == null && repo != null)
            gitHubUrl = "https://github.com/" + repo;

        if (repo != null || gitHubUrl != null)
            addChild(new GitHubLinkWedge(gitHubUrl != null ? gitHubUrl : repo, 0, gitHubUrl != null));
        if (websiteUrl != null)
            addChild(new WebsiteLinkWedge(websiteUrl, 0));

        addChildren(parser);
        addRequest(new RepositoryData(repo));
    }

    @Override
    public void onInit(GitHubData data) {
        if (data instanceof RepositoryData) {
            RepositoryData repository = (RepositoryData) data;
            description = "Este aplicativo visa auxiliar a resolução de exercícios de Matemática Elementar, a partir de desenvolvimento com passo a passo.";

            List<LinkWedge> newLinks = new ArrayList<>();
            if (repository.html_url != null)
                newLinks.add(new GitHubLinkWedge(repository.html_url, 0, true));
            if (repository.homepage != null) {
                newLinks.add(repository.homepage.startsWith("https://play.google.com/")
                        ? new PlayStoreLinkWedge(repository.homepage, 0)
                        : new WebsiteLinkWedge(repository.homepage, 0));
            }

            for (LinkWedge link : newLinks)
                addChild(link);
        }
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void bind(final Context context, ViewHolder viewHolder) {
        ApplicationInfo info = context.getApplicationInfo();
        ResourceUtils.setImage(context, icon, info.icon, viewHolder.appIconView);
        viewHolder.nameTextView.setText(info.labelRes);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(info.packageName, 0);
            viewHolder.versionTextView.setText(String.format(context.getString(R.string.title_attribouter_version), packageInfo.versionName));
            viewHolder.versionTextView.setVisibility(View.VISIBLE);
        } catch (PackageManager.NameNotFoundException e) {
            viewHolder.versionTextView.setVisibility(View.GONE);
        }

        String actualDescription = ResourceUtils.getString(context, description);
        if (actualDescription != null) {
            viewHolder.descriptionTextView.setVisibility(View.VISIBLE);
            viewHolder.descriptionTextView.setText(actualDescription);
        } else viewHolder.descriptionTextView.setVisibility(View.GONE);

        List<LinkWedge> links = getChildren(LinkWedge.class);
        if (links.size() > 0) {
            Collections.sort(links, new LinkWedge.Comparator(context));

            List<Wedge> linksList = new ArrayList<>();
            for (LinkWedge link : links) {
                if (!link.isHidden())
                    linksList.add(link);
            }

            viewHolder.links.setVisibility(View.VISIBLE);

            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(context);
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.CENTER);
            viewHolder.links.setLayoutManager(layoutManager);
            viewHolder.links.setAdapter(new InfoAdapter(linksList));
        } else viewHolder.links.setVisibility(View.GONE);
    }

    protected static class ViewHolder extends Wedge.ViewHolder {

        protected ImageView appIconView;
        protected TextView nameTextView;
        protected TextView versionTextView;
        protected TextView descriptionTextView;
        protected RecyclerView links;

        protected ViewHolder(View v) {
            super(v);
            appIconView = v.findViewById(R.id.appIcon);
            nameTextView = v.findViewById(R.id.appName);
            versionTextView = v.findViewById(R.id.appVersion);
            descriptionTextView = v.findViewById(R.id.description);
            links = v.findViewById(R.id.appLinks);
        }

    }
}
