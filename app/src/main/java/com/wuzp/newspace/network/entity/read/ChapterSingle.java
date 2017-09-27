package com.wuzp.newspace.network.entity.read;

/**
 * Created by wuzp on 2017/9/27.
 */
public class ChapterSingle {
    private StatusBean status = new StatusBean();

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    /**
     * book_id : 5372416
     * chapter_id : 10338020
     * is_vip : N
     * title : 第6章 原来是你
     * content : 44CA44CA56ysNueroCDljp/mnaXmmK/kvaAK4oCc5pei54S25piv5L2g77yM6YKj5L2g6IO95pS+5oiR6LWw5ZCX77yf4oCd5Y2r6bii5bC+55yL552A5LqR6YKq77yM6K+t5rCU5Lit55Wl5bim552A5oGz5rGC44CCCuWlueW4jOacm+S7luiDveeci+WcqOWlueabv+WlueeWl+S8pOeahOS7veS4iuaUvuS6huWlue+8gQrkupHpgqrmvIbpu5HnmoTnnLjlhYnokL3lnKjljavpuKLlsL7ooqvmiZPnmoTnuqLogr/nmoTlj7PohLjkuIrvvIzorqnljavpuKLlsL7ml6Dnq6/ljYfotbfkuIDogqHntKflvKDmhJ/jgIIK4oCc5L2g6L+Z5piv5Zyo5rGC5pys546L77yf4oCd5LqR6YKq5ryG6buR55qE55y45YWJ5Lit5rex6LCZ55qE5aaC5ZCM5rWT56ig55qE5aKo77yM55yL5LiN6KeB5bqV77yM5reh5reh55qE5aOw6Z+z6YCP6Zyy552A5LiA5Lid5Ya35oSP77ya4oCc5LiA6Iis5rGC5pys546L55qE5Lq677yM6YO95piv6Leq552A55qE77yB4oCdCui3qu+8n+WNq+m4ouWwvuS9nOS4uueOsOS7o+S6uu+8jOi/mOecn+eahOaYr+i3quS4jeS4i+adpeOAggrigJznjovniLfvvIzkvaDlsLHnnIvlnKjlhYjliY3miJHmm7/kvaDnlpfkvKTnmoTku73kuIrigKbigKbigJ3ljavpuKLlsL7lkqzkuoblkqzniZnvvIEK4oCc546L5aaD77yM5L2g5pyA5aW95byE5riF5qWa5L2g546w5Zyo55qE6Lqr5Lu977yM5L2g5pyJ5LuA5LmI6LWE5qC86Lef5pys546L6K6y5p2h5Lu277yf4oCd5LqR6YKq5LiA5Y+M5ryG6buR55qE55y46JC95Zyo5Y2r6bii5bC+55qE6Lqr5LiK44CCCueerOmXtOWNq+m4ouWwvuaEn+inieWRqOi6q+mDveiiq+WGt+aEj+eOr+e7leOAggrigJzlpoLmnpzov5nkuYjor7TvvIzmnKznjovkuYvliY3ov5jmlZHov4fkvaDkuIDlkb3vvIzlpoLmraTnrpfmnaXvvIzkvaDov5jmrKDmnKznjovkuIDmnaHlkb3vvIzmlZHlkb3kuYvmganvvIznkIblvZPku6Xouqvnm7jorrjkuI3mmK/lkJfvvJ/igJ3kupHpgqrpnaLlhbfkuIvnmoTllIfop5Llvq7li77vvIzkuIDogqHlh4znm5vnmoTmsJTmga/oh6rlkajouqvmlaPlj5Hlh7rmnaXjgIIK5ZGo5Zu055qE56m65rCU556s6Ze05Y+Y5b6X5Y2B5YiG55qE5Y6L5oqR77yM6K6p5Y2r6bii5bC+5pyJ5Lqb5ZaY5LiN6L+H5rCU5p2l44CCCuWvue+8jOWmguaenOS4jeaYr+S7luaVkei/h+Wlue+8jOi/mOabv+WlueadvuS6hue7ke+8jOWlueaJjeS4jeS8muWlveW/g+eahOW4ruS7lue8neWQiOS8pOWPo++8gQrigJzlj6/mmK/igKbigKbmiJHmgJXmrbvvvIHigJ3ljavpuKLlsL7mt7HlkLjkuobkuIDlj6PmsJTvvIzlv4Pph4zmhKTmgajkuI3lt7LjgIIK6L+Z5Liq6YKq546L55yf5piv5aSa5Y+Y77yM5Yia5byA5aeL5aW56L+Y6KeJ5b6X5LuW5oy65aW96K+06K+d55qE77yM57uT5p6c6K+d57yd5LiA6L2s6K+t5rCU56uf54S25Y+Y5b6X5aaC5q2k54qA5Yip44CCCuKAnOaAleatu+S9oOi/mOaVoui3n+acrOeOi+aPkOadoeS7tu+8n+KAneS6kemCquWUh+inkuWLvuWLkueahOW8p+W6pui2iuWPkea3semCg++8jOecuOWFieiHquWni+iHs+e7iOayoeS7juWNq+m4ouWwvueahOiEuOS4iuenu+W8gO+8muKAnOaKiuiEuOaKrOi1t+adpe+8geKAnQrljavpuKLlsL7nmoTlpLTkuI3mmK/kvY7lnoLnmoTvvIzlj43ogIzlvq7lvq7miazotbfvvIzkvYbmmK/ljavpuKLlsL7nmoTouqvpq5jlj6rliLDkupHpgqrnmoTog7jlj6PvvIzmiYDku6XkupHpgqrnnIvkuI3muIXljavpuKLlsL7nmoTlhajohLjjgIIK6KeB5Y2r6bii5bC+5rKh5pyJ5Y+N5bqU77yM5LqR6YKq5Ly45Ye65L+u6ZW/55qE5omL77yM6L275o2P5L2P5Y2r6bii5bC+55qE5LiL5be05oqs6LW35aW555qE6IS444CCCuWNq+m4ouWwvue6ouiCv+eahOWPs+iEuOS4gOS4i+WwseiQveWFpeS6huS6kemCqueahOiEuOOAggrigJzosIHmiZPnmoTvvJ/igJ3kuIDovrnor7TnnYDvvIzkupHpgqrlvq7lvq7mnInkupvlhrDlh4nnmoTmiYvmjIfkvr/ovbvliJLov4fljavpuKLlsL7nmoTlj7PohLjpoorvvIzlpoLooYzkupHmtYHmsLToiKzvvIzliqjkvZzljYHliIbnmoTovbvmn5TjgIIK5Y2r6bii5bC+5LiN6K+06K+d44CCCuKAnOacrOeOi+iuqeS9oOivtOivne+8geKAnQrigJzlkYror4nkvaDvvIzkvaDov5jog73luK7miJHmiZPlm57mnaXlkJfvvJ/igJ3ljavpuKLlsL7muIXkuL3nmoTnnLjlhYnkuK3mn5PkuIrkuI3ogJDvvIzkuIDlvKDnsr7oh7TnmoTpnaLlrrnooqvkupHpgqrovbvovbvnmoTmjY/lnKjmiYvkuK3vvIzlpoLlkIzmmJPnoo7nmoTpmbbnk7foiKzvvIzlnKjmqZjpu4ToibLng5vlhYnnmoTmjqnmmKDkuIvvvIznsr7nvo7nmoTkuI3lg4/or53jgIIK4oCc5L2g5pei54S25piv5pys546L55qE5Lq65LqG77yM5pys546L6Ieq54S25Lya5pu/5L2g6K6o5Zue5YWs6YGT77yB4oCd5LqR6YKq6JaE5YmK55qE5ZSH5LiA5byg5LiA5Y+j77yM6L276L2755qE5ZCQ5Ye66L+Z5Yeg5Liq5a2X77yM5Y205LiN5aSx6Zy46YGT44CCCuKAnOaWsOmDjuaWsOWomOivpeWWneWQiOWNuumFkuS6hu+8geKAneWWnOWphuWcqOmXqOWkluS4gOebtOWQrOedgO+8jOingeS4pOS4quS6uuayoeacieWKqOmdme+8jOS+v+S4jeaAleatu+eahOi1sOi/m+adpeOAggrkupHpgqrmjqXov4fllpzlqYbpgJLov4fmnaXnmoTllpzphZLvvIzogIzljavpuKLlsL7lnKjkupHpgqrnlaXluKbnnYDlqIHog4HnmoTnm67lhYnkuK3mjqXkuIvmnaXkuobjgIIK5Lik5Lq65Y+M5omL5Lqk5o2i77yM5Y2r6bii5bC+5Yia5YeG5aSH5Zad5LiL5Zac6YWS77yM6ISR5a2Q6YeM5Y2056uL5Yi75ZON6LW36K2m6ZOD77yM5Y+k5Luj5Lq65oiQ5Lqy5Y+v5piv5Lya5Zyo5bCx6YWS6YeM5LiL6I2v55qE44CCCuS6juaYr+WNq+m4ouWwvuS+v+WwhumFkuWQq+WcqOWYtOmHjO+8jOayoeWSveS4i+WOu+OAggrigJzmlrDpg47mlrDlqJjllp3lrozlkIjljbrphZLlsLHmtJ7miL/lkKfvvIHigJ3llpzlqYbnrJHlmLvlmLvnmoTotbDliLDnqpfliY3mi7/lh7rkuIDlnZflhL/nuq/nmb3oibLnmoTmlrnlt77pk7rlnKjnu6PmnInpuLPpuK/nmoTluorpk7rkuIrvvIzlj4jor7TkuobkuIDkupvml6nnlJ/otLXlrZDnmoTlkInnpaXor53lsLHpgIDkuoblh7rljrvjgIIK4oCc5ZK95LiL5Y6777yB4oCd5LqR6YKq55qE5aOw6Z+z5Ya35LiN5LiB55qE5LuO5aS06aG25Lyg5p2l44CCCuWNq+m4ouWwvuaKv+edgOWYtOW3tO+8jOeequedgOecvOedm+eci+edgOS6kemCquOAggrkupHpgqrmjY/kvY/ljavpuKLlsL7nmoTkuIvlt7TvvIzmiLTnnYDpk7boibLpnaLlhbfnmoTpnaLlrrnkuIDkuIvpnaDov5HljavpuKLlsL7vvIzljavpuKLlsL7ku6XkuLrkupHpgqrmg7PopoHkurLlpbnvvIzlkJPlvpflpbnnq4vliLvlsIblmLTkuK3nmoTphZLnu5nlkr3kuobkuIvljrvjgIIK5Y2r6bii5bC+56C45LqG56C45Zi05be077yM6L+Z6YWS5oy655Sc55qE77yM5LiN5Ly855m96YWS6YKj5LmI6L6j77yM6LKM5Ly86L+Y5oy65aW95Zad44CCCuS5n+S4jeefpemBk+acieayoeacieS4i+iNr++8jOimgeaYr+S4i+iNr+mCo+WwseWujOibi+S6hu+8gQrmraPmg7PnnYDljavpuKLlsL7mhJ/op4nohbDkuIrkuIDntKfvvIzkupHpgqrnmoTlpKfmiYvkvr/njq/lnKjkuobljavpuKLlsL7nmoTohbDkuIrjgIIK4oCc546L5aaD77yM5oiR5Lus6K+l5rSe5oi/5LqG77yB4oCd5LqR6YKq5oKg6ZW/5Y+I5bim552A5oiP6LCR55qE5aOw6Z+z5Zyo5Y2r6bii5bC+55qE6ICz6L655Lyg5p2l44CCCuWNq+m4ouWwvua1kei6q+S4gOaEo++8jOaEn+iniei6q+S9k+eahOavj+S4quavm+WtlOmDveerlui1t+adpeS6hu+8jOWSveS6huWSveWPo+awtO+8muKAnOaIkeKApuKApuaIkeeqgeeEtuinieW+l+aDs+S4iuWOleaJgO+8geKAnQror7TnnYDljavpuKLlsL7lsLHmjoDotbfoo5nlrZDmnJ3pl6jlpJbotbDljrvvvIzmlL7lnKjljavpuKLlsL7ohbDkuIrnmoTlpKfmiYvlv73ogIznlKjlipvvvIzkuIDkuIvlsLHlsIbljavpuKLlsL7nu5nmirHliLDkuobluorkuIrjgIIK4oCc546L54i34oCm4oCm546L54i377yM5L2g6Lqr5LiK5pyJ5Lyk77yM5LiN5a6c4oCm4oCm5LiN5a6c5YGa5Ymn54OI6L+Q5Yqo77yB4oCd5Y2r6bii5bC+57Sn5byg55qE6K+d6YO96K+05LiN5YWo5LqG44CCCuKAnOaXoOWmqO+8geKAneS6kemCquWOi+S4iuWNq+m4ouWwvueahOi6q++8jOS4gOaJi+aSkeWcqOWNq+m4ouWwvuiEkeiii+aXge+8jOS4gOaJi+S+v+W8gOWni+ino+WNq+m4ouWwvuiho+acjeeahOiFsOW4puOAggrigJznjovniLfvvIznjovniLfigKbigKbmiJHigKbigKbmiJHnnJ/nmoTopoHkuIrojIXmiL/vvIHigJ3ljavpuKLlsL7nq4vliLvliLbmraLkvY/kupHpgqrnmoTliqjkvZzvvIzljbTlj43lgJLooqvkupHpgqrlj43miaPkvY/lj4zmiYvjgIIK4oCc5Yir5YaN5pys546L6Z2i5YmN6ICN6L+Z5Lqb5bCP6IGq5piO77yB4oCd5LqR6YKq6aqo6IqC5YiG5piO55qE5omL6L276L275LiA5omv77yM5Y2r6bii5bC+6IWw5LiK55qE6IWw5bim5bCx6KKr6Kej5byA44CCCuS4gOi6q+eBq+e6oueahOWWnOacjeWmgue6oueOq+eRsOiIrOWmluWohueahOebm+W8gOWcqOWQjOagt+e6ouiJs+eahOW6iuS4iu+8jOe+juS4veiAjOWPiOiJs+e7muS4ve+8gQrigJzkvaDorqnmiJHljrvkuIDkuIvvvIzljrvljrvlsLHmnaXvvIHigJ3ljavpuKLlsL7nmoTlj4zmiYvooqvkupHpgqrlj43miaPkvY/vvIzliqjlvLnkuI3lvpfvvIznnLznnZvntKfntKfnmoTnm6/nnYDkupHpgqrnmoTmiYvjgIIK4oCc5L2g55+l6YGT56ys5Zub5Lu7546L5aaD5piv5oCO5LmI5q2755qE5ZCX77yf5aW55Lmf6Lef5pys546L6K+05aW56KaB5Y676IyF5oi/77yM5LmL5ZCO5aW555qE5bC45L2T5bCx6KKr5Lq65oqs5LqG5Zue5p2l77yB4oCd5LqR6YKq55qE5aOw6Z+z5L2O5ZCf5Ya35Ya944CCCuiuqeWNq+m4ouWwvuWQrOW+l+S4jeWvkuiAjOagl++8jOWOn+adpei/meS4gOaLm+S4jeatouWlueS4gOS4quS6uueUqOi/h+WViu+8gQrkupHpgqrkv6/kuIvouqvvvIzmuKnng63nmoTmsJTmga/miZHlnKjljavpuKLlsL7nmoTohLjkuIrvvIznl5Lnl5LnmoTvvIzoloTliYrnmoTllIfkvLzonLvonJPngrnmsLToiKzngrnov4fljavpuKLlsL7ohLjkuIrnmoTogozogqTjgIIK5q+P5a+46KKr54K56L+H55qE6IKM6IKk5aaC5ZCM54Gr54On5LqR5LiA6Iis77yM54G854Ot55qE5Y+R552A54Or44CCCuWNq+m4ouWwvuaDs+imgeWwvemHj+mBv+W8gOS6kemCqueahOWUh++8jOWPr+aYr+Wluei2iui6su+8jOS6kemCquWwsemAvOW+l+i2iue0p+OAggrmraPlnKjkupHpgqrnu6fnu63kuIvkuIDmraXliqjkvZznmoTml7blgJnvvIzkuIDpmLXnu57nl5vooq3mnaXvvIznlrznmoTljavpuKLlsL7pob7kuI3kuIrpmLvmjKHkupHpgqrnmoTliqjkvZzvvIzovazogIzmlL7lnKjkuobogprlrZDkuIrvvIznnInlpLTntKfnmrHnnYDvvIzpgqPnp43nl5vlsLHlpoLog4Pnl4nmjJvkuIDoiKzvvIzorqnljavpuKLlsL7nl5vnmoTmsqHmnInml7bpl7TmgJ3ogIPlhbbku5bnmoTkuJzopb/jgIIK5LqR6YKq5YGc5LiL5omL5Lit55qE5Yqo5L2c77yM5ryG6buR55qE55y45Lit5ruh5piv5oyl5LmL5LiN5bC955qE54K954Ot5ZKMKirvvJrigJzmgI7kuYjkuobvvJ/igJ0K4oCc55eb4oCm4oCm5aW955eb4oCm4oCm4oCd5Y2r6bii5bC+55eb55qE5pW05byg5bCP6IS455qx6LW377yM6Zq+5Y+X55qE5Zyo5bqK5LiK57+75rua77yM6L+e6K+d6YO96K+05LiN5riF5qWa77yM5Lid5Lid55qE5Ya35rGX5LuO6aKd5aS077yM6Lqr5LiK5riX6YCP5Ye65p2l44CCCuWNq+m4ouWwvuWQjuaClOS6hu+8jOS4jeivpeWQg+mCo+S5iOWkmueahO+8jOeOsOWcqOiCmuWtkOeWvOeahOWlueaDs+atu+OAggrkupHpgqrnq4vljbPnv7vouqvkuIvluornqb/kuIrooaPoo7PvvIzlr7nnnYDpl6jlpJbllorpgZPvvJrigJznjoTnprvvvIzljrvor7flpKflpKvvvIHigJ0K562J5aSq5Yy75p2l5pe277yM5Y2r6bii5bC+5Zyo5bqK5LiK5bey57uP55eb55qE5q275Y675rS75p2l77yM6Z2i6Imy5oOo55m977yM5LiN5L2P55qE5ZG75ZCf44CCCuS4jeaYr+ivtOS8muiuqeWlueaLieiCmuWtkOeahOWQl++8n+S4uuS7gOS5iOiCmuWtkOS8mui/meS5iOeXm+WViu+8n+iAjOS4lOWlueS4gOeCueWEv+aLieiCmuWtkOeahOi/ueixoemDveayoeacieWViu+8gQrlpKrljLvpmpTnnYDluJjlrZDnu5nljavpuKLlsL7miorkuobohInvvIznnLzlhYnkuI3nlLHnmoTlnKjmiL/pl7Tmiavop4bkuobkuIDnnLzvvIzpmo/ljbPovbvlj7nkuIDlo7DvvIzmiJjmiJjlhaLlhaLnmoTlm57npoDpgZPvvJrigJznjovniLfvvIznjovlpoPnmoTouqvlrZDpqqjlpKrlvLHvvIzkuIDml7bmtojljJbkuI3kuoblkIPkuIvnmoTpo5/nianvvIzpo5/nianloIbnp6/lnKjogqDpgZPph4zvvIzogIzkuJTnjovlpoPov5jlkIPkuoblpKfph4/nmoTmgKflh4nkuI3mmJPmtojljJbnmoTpo5/nianvvIzov5nmiY3lr7zoh7Tohbnpg6jliafnl5vvvIzogIHoh6PlvIDmnI3oja/llp3kuIvvvIznlrznl5vkvr/kvJrlh4/ovbvvvIzlj6rmmK/igKbigKbigJ0=
     * pre_chapter : {"chapter_id":"10338019","chapter_name":"第5章 拜堂","is_vip":"N","order_num":"5","s_num":5}
     * next_chapter : {"chapter_id":"10338021","chapter_name":"第7章 催情酒","is_vip":"N","order_num":"7","s_num":7}
     */

    private String book_id;
    private String chapter_id;
    private String is_vip;
    private String title;
    private String content;
    /**
     * chapter_id : 10338019
     * chapter_name : 第5章 拜堂
     * is_vip : N
     * order_num : 5
     * s_num : 5
     */

    private PreChapterBean pre_chapter;
    /**
     * chapter_id : 10338021
     * chapter_name : 第7章 催情酒
     * is_vip : N
     * order_num : 7
     * s_num : 7
     */

    private NextChapterBean next_chapter;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PreChapterBean getPre_chapter() {
        return pre_chapter;
    }

    public void setPre_chapter(PreChapterBean pre_chapter) {
        this.pre_chapter = pre_chapter;
    }

    public NextChapterBean getNext_chapter() {
        return next_chapter;
    }

    public void setNext_chapter(NextChapterBean next_chapter) {
        this.next_chapter = next_chapter;
    }

    public static class PreChapterBean {
        private String chapter_id;
        private String chapter_name;
        private String is_vip;
        private String order_num;
        private String s_num;

        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getS_num() {
            return s_num;
        }

        public void setS_num(String s_num) {
            this.s_num = s_num;
        }
    }

    public static class NextChapterBean {
        private String chapter_id;
        private String chapter_name;
        private String is_vip;
        private String order_num;
        private String s_num;

        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getChapter_name() {
            return chapter_name;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getS_num() {
            return s_num;
        }

        public void setS_num(String s_num) {
            this.s_num = s_num;
        }
    }
}
