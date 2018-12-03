package dig.big.com.appa.sql;

import java.util.List;

public interface IDatabaseHandler {
    public void addLink(Link link);
    public Link getLink(int id);
    public List<Link> getAllLinks();
    public int getLinksCount();
    public int updateLink(Link link);
    public void deleteLink(Link link);
    public Link findLink(String s);

}
