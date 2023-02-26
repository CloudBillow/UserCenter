package com.miracle.usercenter.util;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 */
@Component
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class RedisUtils {

    @Resource(name = "user.center.redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;


    /* -------------------key相关操作--------------------- */

    /**
     * 删除一个指定 key
     *
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个指定 key
     *
     * @param keys 键集合
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 获取存储在Redis中指定 key 的 value 的序列化版本
     *
     * @param key 键
     * @return 序列化后的 key
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 检查 key 是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 仅当 key 存在时，设置 key 在指定时间之后过期
     *
     * @param key     键
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 是否设置成功
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 仅当 key 存在时，设置 key 在指定日期之后过期
     *
     * @param key  键
     * @param date 过期日期
     * @return 是否设置成功
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    /**
     * 仅当 key 存在时，设置 key 在指定日期之后过期
     *
     * @param key  键
     * @param date 过期日期
     * @return 是否设置成功
     */
    public Boolean expireAt(String key, LocalDateTime date) {
        return redisTemplate.expireAt(key, Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * 查找与指定模式匹配的所有 key
     * <p>
     * 注意：key 较多时建议使用 {@link #sScan(String, ScanOptions)}
     *
     * @param pattern 正则表达式
     * @return key集合
     */
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 当且仅当 key 存在，且目标库不存在指定 key 时，将 key 移动到目标库
     * <p>
     * 注意：目标库和当前库不能相同
     *
     * @param key     键
     * @param dbIndex 数据库索引
     * @return 是否移动成功
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 仅仅当 key 存在过期时间时，移除 key 的过期时间，key 将持久保持
     *
     * @param key 键
     * @return 是否移除成功
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key  键
     * @param unit 时间单位
     * @return 剩余的过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     *
     * @param key 键
     * @return 剩余的过期时间（当 key 未设置过期时间时返回<code>-1</code>，当 key 不存在时返回<code>-2</code>）
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return 随机的 key （当库中不存在 key 时返回 <code>null</code>）
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 仅当 oldKey 存在时，修改 oldKey 的名称，如果 newKey 在库中已经存在，则会将其对应的值覆盖
     *
     * @param oldKey 旧键
     * @param newKey 新键
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 oldKey 存在且 newKey 不存在时，将 oldKey 改名为 newKey
     *
     * @param oldKey 旧键
     * @param newKey 新键
     * @return 是否修改成功（如果 newKey 在库中已经存在则返回 <code>false</code>）
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的数据类型
     *
     * @param key 键
     * @return key 所储存的值的类型
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /* -------------------string相关操作--------------------- */

    /**
     * 设置指定 key 的 value
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 获取指定 key 的 value
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForValue().get(key));
    }

    /**
     * 返回指定 key 的 value 的子字符（串）
     * <p>
     * 注意：如果 key 不存在，返回 <code>""</code>（空串）
     *
     * @param key   键
     * @param start 开始索引（从0开始）
     * @param end   结束索引（包含）
     * @return 子字符（串）
     */
    public String getRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    /**
     * 将给定 key 的 value 设为指定的 newValue ，并返回 key 的旧 value
     * <p>
     * 若给定的 key 不存在，则创建一个新的 key，其 value 为 newValue
     *
     * @param key      键
     * @param newValue 值
     * @return 旧值
     */
    public <T> T getAndSet(String key, Object newValue, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForValue().getAndSet(key, newValue));
    }

    /**
     * 对 key 所储存的 value，获取指定偏移量上的位(bit)
     * <p>
     * 例如：在ASCII码中，字符 'a' 的ASCII码是97, 转为二进制是 '01100001'，如果 offset 为 6，则返回 false（0）
     *
     * @param key    键
     * @param offset 偏移量
     * @return 偏移量上的位。当偏移量 offset 比字符串值的长度大，或者 key 不存在时返回 0
     */
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 根据 key 列表批量获取 value 列表
     *
     * @param keys 键集合
     * @return 值集合
     */
    public <T> List<T> multiGet(Collection<String> keys, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForValue().multiGet(keys))
                .stream().map(clazz::cast).collect(Collectors.toList());
    }

    /**
     * 将指定 key 的二进制第 offset 位值变为 value
     * <p>
     * 例如：在ASCII码中，字符 'a' 的ASCII码是97, 转为二进制是 '01100001'，如果 offset 为 6，则变为 '01100011'，即字符 'c'
     * <p>
     * 若 key 不存在则会创建一个新的 key
     *
     * @param key      键
     * @param position 位置
     * @param value    值(true为1, false为0)
     * @return position 位置上原来的值（1为true, 0为false）
     */
    public Boolean setBit(String key, long position, boolean value) {
        return redisTemplate.opsForValue().setBit(key, position, value);
    }

    /**
     * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
     * <p>
     * 若 key 已经存在，则将其覆盖；若 key 不存在，则创建一个新的 key
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void setEx(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 只有在 key 不存在时设置 key 的 value
     *
     * @param key   键
     * @param value 值
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 只有在 key 不存在时设置 key 的 value, 并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     * @return 之前已经存在返回false, 不存在返回true
     */
    public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 用 value 参数覆写给定 key 所储存的 value，从偏移量 offset 开始
     *
     * @param key    键
     * @param value  值
     * @param offset 偏移量（起始值为0）
     */
    public void setRange(String key, Object value, long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

    /**
     * 获取指定 key 的 value 的长度
     *
     * @param key 键
     * @return 字符串的长度
     */
    public Long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 批量添加 key-value 对
     * <p>
     * 已经存在的 key 的 value 将被覆盖
     *
     * @param maps 键值对集合
     */
    public void multiSet(Map<String, Object> maps) {
        redisTemplate.opsForValue().multiSet(maps);
    }

    /**
     * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在时才会生效
     *
     * @param maps 键值对集合
     * @return 之前已经存在返回 <code>false</code> 且不会添加任何 key, 都不存在返回 <code>true</code> 且添加所有 key
     */
    public Boolean multiSetIfAbsent(Map<String, Object> maps) {
        return redisTemplate.opsForValue().multiSetIfAbsent(maps);
    }

    /**
     * 自增指定 key 的 value, 负数则为自减
     * <p>
     * 若给定的 key 不存在，则其初始值为 0 ，然后再执行自增操作
     * <p>
     * 注意：如果 value 的类型不为 <code>integer</code>，那么该操作会抛出异常
     *
     * @param key       键
     * @param increment 增量
     * @return 自增后的值
     */
    public Long incrBy(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 自增指定 key 的 value, 负数则为自减
     * <p>
     * 若给定的 key 不存在，则其初始值为 0 ，然后再执行自增操作
     * <p>
     * 注意：如果 value 的类型不为 <code>float</code> 或 <code>integer</code>，那么该操作会抛出异常
     *
     * @param key       键
     * @param increment 增量
     * @return 自增后的值
     */
    public Double incrByFloat(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    /**
     * 将字符串 value 追加到指定 key 的末尾
     *
     * @param key   键
     * @param value 值
     * @return 追加后字符串的长度
     */
    public Integer append(String key, String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    /* -------------------hash相关操作------------------------- */

    /**
     * 获取哈希表 key 中指定 hashKey 的值
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return hashKey 对应的 hashValue
     */
    public <T> T hGet(String key, String hashKey, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForHash().get(key, hashKey));
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key 键
     * @return 值集合
     */
    public <T> Map<String, T> hGetAll(String key, Class<T> clazz) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        Map<String, T> map = new HashMap<>(entries.size());
        entries.forEach((k, v) -> map.put(k.toString(), clazz.cast(v)));
        return map;
    }

    /**
     * 获取所有给定字段的值
     *
     * @param key    键
     * @param fields 项集合
     * @return 值集合
     */
    public <T> List<T> hMultiGet(String key, Collection<Object> fields, Class<T> clazz) {
        return redisTemplate.opsForHash().multiGet(key, fields).stream().map(clazz::cast).collect(Collectors.toList());
    }

    /**
     * 将哈希表 key 中的 hashKey 对应的值设为 hashValue
     * <p>
     * 已存在的 hashKey 的值将被覆盖
     *
     * @param key       键
     * @param hashKey   哈希键
     * @param hashValue 哈希值
     */
    public void hPut(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 批量将哈希表 key 中的哈希键 hashKey 的值设为 hashValue
     * <p>
     * 已存在的 hashKey 的值将被覆盖
     *
     * @param key             键
     * @param hashKeyValueMap 哈希键值对集合
     */
    public void hPutAll(String key, Map<String, Object> hashKeyValueMap) {
        redisTemplate.opsForHash().putAll(key, hashKeyValueMap);
    }

    /**
     * 仅当 hashKey 都不存在时才生效
     *
     * @param key       键
     * @param hashKey   哈希键
     * @param hashValue 哈希值
     * @return 之前已经存在返回false, 不存在并设置成功返回true
     */
    public Boolean hPutIfAbsent(String key, String hashKey, Object hashValue) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, hashValue);
    }

    /**
     * 删除一个或多个哈希表 key 中的 hashKey
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return 删除的个数
     */
    public Long hDelete(String key, String... hashKey) {
        return redisTemplate.opsForHash().delete(key, (Object[]) hashKey);
    }

    /**
     * 查看哈希表 key 中，指定的字段是否存在
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return 存在返回true，不存在返回false
     */
    public boolean hExists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 为哈希表 key 中的指定 hashKey 的整数 hashValue 加上增量 increment
     * <p>
     * 当增量 increment 为负数时，相当于对 hashValue 减去 increment
     *
     * @param key       键
     * @param hashValue 哈希值
     * @param longValue 增量
     * @return 自增后的值
     */
    public Long hIncrBy(String key, String hashValue, long longValue) {
        return redisTemplate.opsForHash().increment(key, hashValue, longValue);
    }

    /**
     * 为哈希表 key 中的指定 hashKey 的浮点数 hashValue 加上增量 increment
     * <p>
     * 当增量 increment 为负数时，相当于对 hashValue 减去 increment
     *
     * @param key         键
     * @param hashKey     哈希键
     * @param doubleValue 增量
     * @return 自增后的值
     */
    public Double hIncrByFloat(String key, String hashKey, double doubleValue) {
        return redisTemplate.opsForHash().increment(key, hashKey, doubleValue);
    }

    /**
     * 获取哈希表 key 中的所有 hashKey
     *
     * @param key 键
     * @return hashKey 集合
     */
    public Set<String> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key).stream().map(Object::toString).collect(Collectors.toSet());
    }

    /**
     * 获取哈希表 key 中 hashKey 的数量
     *
     * @param key 键
     * @return hashKey 的数量
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 获取哈希表 key 中所有 hashValue
     *
     * @param key 键
     * @return hashValue 集合
     */
    public <T> List<T> hValues(String key, Class<T> clazz) {
        return redisTemplate.opsForHash().values(key).stream().map(clazz::cast).collect(Collectors.toList());
    }

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     键
     * @param options 选项
     * @return 迭代器
     */
    public Cursor<Map.Entry<Object, Object>> hScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    /* ------------------------list相关操作---------------------------- */

    /**
     * 通过索引获取列表 key 中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0为表头，1为第二个元素，依次类推；index<0时，-1为表尾，-2为倒数第二个元素，依次类推
     * @return value
     */
    public <T> T lIndex(String key, long index, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().index(key, index));
    }

    /**
     * 获取列表 key 指定索引范围的 value
     *
     * @param key        键
     * @param startIndex 开始位置, 从0开始
     * @param endIndex   结束位置, -1返回所有
     * @return value 集合
     */
    public <T> List<T> lRange(String key, long startIndex, long endIndex, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForList().range(key, startIndex, endIndex))
                .stream().map(clazz::cast).collect(Collectors.toList());
    }

    /**
     * 向列表 key 的头部插入 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lLeftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 向列表 key 的头部插入多个 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lLeftPushAll(String key, Object... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 向列表 key 的头部插入多个 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lLeftPushAll(String key, Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当列表 key 存在的时，向列表 key 的头部插入 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lLeftPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 如果目标值 target 存在, 向第一个目标值 target 前面添加
     *
     * @param key    键
     * @param target 目标值
     * @param value  值
     * @return 存储后列表的长度
     */
    public Long lLeftPush(String key, Object target, Object value) {
        return redisTemplate.opsForList().leftPush(key, target, value);
    }

    /**
     * 向列表 key 的尾部插入 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向列表 key 的尾部插入多个 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lRightPushAll(String key, Object... value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 向列表 key 的尾部插入多个 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lRightPushAll(String key, Collection<Object> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 当列表 key 存在的时，向列表 key 的尾部插入 value
     *
     * @param key   键
     * @param value 值
     * @return 存储后列表的长度
     */
    public Long lRightPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 如果目标值 target 存在, 向第一个目标值 target 后面添加
     *
     * @param key    键
     * @param target 目标值
     * @param value  值
     * @return 存储后list的长度
     */
    public Long lRightPush(String key, Object target, Object value) {
        return redisTemplate.opsForList().rightPush(key, target, value);
    }

    /**
     * 将列表 key 下标为 index 的元素的值覆盖设置为 value
     *
     * @param key   键
     * @param index 位置 index>=0时， 0为表头，1为第二个元素，依次类推；index<0时，-1为表尾，-2为倒数第二个元素，依次类推
     * @param value 值
     */
    public void lSet(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 弹出并获取列表 key 的第一个 value
     *
     * @param key 键
     * @return 弹出的 value
     */
    public <T> T lLeftPop(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().leftPop(key));
    }

    /**
     * 弹出并获取列表 key 的第一个元素， 如果列表中没有元素，则会阻塞列表直到等待超时抛出异常或发现可弹出的 value 为止
     *
     * @param key     键
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 弹出的 value
     */
    public <T> T lBLeftPop(String key, long timeout, TimeUnit unit, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().leftPop(key, timeout, unit));
    }

    /**
     * 弹出并获取列表 key 的最后一个 value
     *
     * @param key 键
     * @return 弹出的 value
     */
    public <T> T lRightPop(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().rightPop(key));
    }

    /**
     * 弹出并获取列表 key 的最后一个元素， 如果列表中没有元素，则会阻塞列表直到等待超时抛出异常或发现可弹出的 value 为止
     *
     * @param key     键
     * @param timeout 等待时间
     * @param unit    时间单位
     * @return 弹出的 value
     */
    public <T> T lBRightPop(String key, long timeout, TimeUnit unit, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().rightPop(key, timeout, unit));
    }

    /**
     * 移除列表 sourceKey 的最后一个元素，并将该元素添加到目标列表 targetKey 的头部并返回 value
     *
     * @param sourceKey 源键
     * @param targetKey 目标键
     * @return 移动的元素
     */
    public <T> T lRightPopAndLeftPush(String sourceKey, String targetKey, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, targetKey));
    }

    /**
     * 从列表 sourceKey 中弹出一个值，将弹出的元素插入到另外一个列表 targetKey 的头部并返回
     * <p>
     * 如果列表 sourceKey 中没有元素会阻塞列表直到等待超时抛出异常或发现可弹出元素为止
     *
     * @param sourceKey 源键
     * @param targetKey 目标键
     * @param timeout   等待时间
     * @param unit      时间单位
     * @return 移动的元素
     */
    public <T> T lBRightPopAndLeftPush(String sourceKey, String targetKey,
                                       long timeout, TimeUnit unit, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                targetKey, timeout, unit));
    }

    /**
     * 删除列表 key 中值为 value 的元素
     *
     * @param key   键
     * @param count count=0, 删除所有值等于 value 的元素; count>0, 从头部开始删除 count 个值等于value的元素;
     *              count<0, 从尾部开始删除 count 个值等于value的元素;
     * @param value 值
     * @return 删除的个数
     */
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 对列表 key 进行修剪，只保留指定区间内的元素
     *
     * @param key   键
     * @param start 开始索引（从0开始）
     * @param end   结束索引（包含。当 end<0 时，-1 表示到最后一个元素，-2 表示到倒数第二个元素，以此类推）
     */
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return 列表长度
     */
    public Long lLen(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /* --------------------set相关操作-------------------------- */

    /**
     * 向指定集合 key 添加多个 value
     *
     * @param key    键
     * @param values 值
     * @return 添加的 value 个数
     */
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 从指定集合 key 移除多个 value
     *
     * @param key    键
     * @param values 值
     * @return 移除的个数
     */
    public Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合 key 的一个随机 value
     *
     * @param key 键
     * @return 被移除的随机元素
     */
    public <T> T sPop(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForSet().pop(key));
    }

    /**
     * 将指定的 value 从一个集合 key 移到另一个集合 targetKey
     *
     * @param key       键
     * @param value     值
     * @param targetKey 目标键
     * @return 成功: true, 失败: false
     */
    public Boolean sMove(String key, Object value, String targetKey) {
        return redisTemplate.opsForSet().move(key, value, targetKey);
    }

    /**
     * 获取集合 key 的大小
     *
     * @param key 键
     * @return 集合大小
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断集合 key 中是否包含 value
     *
     * @param key   键
     * @param value 值
     * @return 包含: true, 不包含: false
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取两个集合 key 和 otherKey 的交集
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 两个集合的交集
     */
    public <T> Set<T> sIntersect(String key, String otherKey, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().intersect(key, otherKey))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 获取集合 key 与多个集合 otherKeys 的交集
     *
     * @param key       键
     * @param otherKeys 另一个键
     * @return 集合 key 与多个集合 otherKeys 的交集
     */
    public <T> Set<T> sIntersect(String key, Collection<String> otherKeys, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().intersect(key, otherKeys))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 集合 key 与集合 otherKey 的交集存储到集合 targetKey 中
     *
     * @param key       键
     * @param otherKey  另一个键
     * @param targetKey 目标键
     * @return 交集的个数
     */
    public Long sIntersectAndStore(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, targetKey);
    }

    /**
     * 集合 key 与多个集合 otherKeys 的交集存储到集合 targetKey 中
     *
     * @param key       键
     * @param otherKeys 另一个键
     * @param targetKey 目标键
     * @return 交集的个数
     */
    public Long sIntersectAndStore(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 两个集合的并集
     */
    public <T> Set<T> sUnion(String key, String otherKey, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().union(key, otherKey))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 获取 key 集合与多个集合 otherKeys 的并集
     *
     * @param key       键
     * @param otherKeys 其他键
     * @return 并集
     */
    public <T> Set<T> sUnion(String key, Collection<String> otherKeys, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().union(key, otherKeys))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 集合 key 与集合 otherKey 的并集存储到集合 targetKey 中
     *
     * @param key       键
     * @param otherKey  另一个键
     * @param targetKey 目标键
     * @return 并集的个数
     */
    public Long sUnionAndStore(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, targetKey);
    }

    /**
     * 集合 key 与多个集合的并集存储到集合 targetKey 中
     *
     * @param key       键
     * @param otherKeys 另一个键
     * @param targetKey 目标键
     * @return 并集的个数
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key      键
     * @param otherKey 另一个键
     * @return 两个集合的差集
     */
    public <T> Set<T> sDifference(String key, String otherKey, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().difference(key, otherKey))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 获取集合 key 与多个集合 otherKeys 的差集
     *
     * @param key       键
     * @param otherKeys 另一个键
     * @return 集合 key 与多个集合 otherKeys 的差集
     */
    public <T> Set<T> sDifference(String key, Collection<String> otherKeys, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().difference(key, otherKeys))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 集合 key 与集合 otherKey 的差集存储到集合 targetKey 中
     *
     * @param key       键
     * @param otherKey  另一个键
     * @param targetKey 目标键
     * @return 差集的个数
     */
    public Long sDifference(String key, String otherKey, String targetKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, targetKey);
    }

    /**
     * 集合 key 与多个集合 otherKeys 的差集存储到集合 targetKey 中
     *
     * @param key       键
     * @param otherKeys 另一个键
     * @param targetKey 目标键
     * @return 差集的个数
     */
    public Long sDifference(String key, Collection<String> otherKeys, String targetKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, targetKey);
    }

    /**
     * 获取集合 key 的所有 value
     *
     * @param key 键
     * @return 集合所有 value
     */
    public <T> Set<T> sMembers(String key, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().members(key))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 随机获取集合 key 中的一个 value
     *
     * @param key 键
     * @return 集合中的一个随机 value
     */
    public <T> T sRandomMember(String key, Class<T> clazz) {
        return clazz.cast(redisTemplate.opsForSet().randomMember(key));
    }

    /**
     * 随机获取集合 key 中 count 个 value
     *
     * @param key   键
     * @param count 随机获取的元素的个数
     * @return 随机获取的 value 集合
     */
    public <T> List<T> sRandomMembers(String key, long count, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().randomMembers(key, count))
                .stream().map(clazz::cast).collect(Collectors.toList());
    }

    /**
     * 随机获取集合 key 中 count 个 value 并且去除重复的
     *
     * @param key   键
     * @param count 随机获取的元素的个数
     * @return 随机获取的 value 集合
     */
    public <T> Set<T> sDistinctRandomMembers(String key, long count, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForSet().distinctRandomMembers(key, count))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 根据匹配项获取集合 key 中的 value
     *
     * @param key     键
     * @param options 匹配项
     * @return Cursor
     */
    public Cursor<Object> sScan(String key, ScanOptions options) {
        return redisTemplate.opsForSet().scan(key, options);
    }

    /*------------------zSet相关操作--------------------------------*/

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return true 成功 false 失败
     */
    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 批量添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key    键
     * @param values 值
     * @return true 成功 false 失败
     */
    public Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
        return redisTemplate.opsForZSet().add(key, values);
    }

    /**
     * 批量删除元素
     *
     * @param key    键
     * @param values 值
     * @return 删除的个数
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   键
     * @param value 值
     * @param delta 要增加的值
     * @return 增加后的值
     */
    public Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   键
     * @param value 值
     * @return 0表示第一位
     */
    public Long zReverseRank(String key, Object value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 元素集合
     */
    public <T> Set<T> zRange(String key, long start, long end, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().range(key, start, end))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 元素集合
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> zRangeWithScores(String key, long start,
                                                                  long end, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeWithScores(key, start, end))
                .stream().map(typedTuple -> {
                    T value = clazz.cast(typedTuple.getValue());
                    return new DefaultTypedTuple<>(value, typedTuple.getScore());
                }).collect(Collectors.toSet());
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    public <T> Set<T> zRangeByScore(String key, double min, double max, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeByScore(key, min, max))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> zRangeByScoreWithScores(String key,
                                                                         double min, double max, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max))
                .stream().map(typedTuple -> {
                    T value = clazz.cast(typedTuple.getValue());
                    return new DefaultTypedTuple<>(value, typedTuple.getScore());
                }).collect(Collectors.toSet());
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 元素集合
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> zRangeByScoreWithScores(String key,
                                                                         double min, double max, long start, long end, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max,
                start, end)).stream().map(typedTuple -> {
            T value = clazz.cast(typedTuple.getValue());
            return new DefaultTypedTuple<>(value, typedTuple.getScore());
        }).collect(Collectors.toSet());
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 元素集合
     */
    public <T> Set<T> zReverseRange(String key, long start, long end, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRange(key, start, end))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 元素集合
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> zReverseRangeWithScores(String key,
                                                                         long start, long end, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end))
                .stream().map(typedTuple -> {
                    T value = clazz.cast(typedTuple.getValue());
                    return new DefaultTypedTuple<>(value, typedTuple.getScore());
                }).collect(Collectors.toSet());
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    public <T> Set<T> zReverseRangeByScore(String key, double min, double max, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeByScore(key, min, max))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 元素集合
     */
    public <T> Set<ZSetOperations.TypedTuple<T>> zReverseRangeByScoreWithScores(
            String key, double min, double max, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max))
                .stream().map(typedTuple -> {
                    T value = clazz.cast(typedTuple.getValue());
                    return new DefaultTypedTuple<>(value, typedTuple.getScore());
                }).collect(Collectors.toSet());
    }


    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return 元素集合
     */
    public <T> Set<T> zReverseRangeByScore(String key, double min, double max,
                                           long start, long end, Class<T> clazz) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end))
                .stream().map(clazz::cast).collect(Collectors.toSet());
    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 元素数量
     */
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return 集合大小
     */
    public Long zZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   键
     * @param value 元素
     * @return score值
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 移除的个数
     */
    public Long zRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return 移除的个数
     */
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key      键
     * @param otherKey 其他键
     * @param destKey  存储键
     * @return 集合大小
     */
    public Long zUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key       键
     * @param otherKeys 其他键集合
     * @param destKey   存储键
     * @return 集合大小
     */
    public Long zUnionAndStore(String key, Collection<String> otherKeys,
                               String destKey) {
        return redisTemplate.opsForZSet()
                .unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取key和otherKey的交集并存储在destKey中
     *
     * @param key      键
     * @param otherKey 其他键
     * @param destKey  存储键
     * @return 集合大小
     */
    public Long zIntersectAndStore(String key, String otherKey,
                                   String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * 获取key和otherKey的交集并存储在destKey中
     *
     * @param key       键
     * @param otherKeys 其他键集合
     * @param destKey   存储键
     * @return 集合大小
     */
    public Long zIntersectAndStore(String key, Collection<String> otherKeys,
                                   String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 匹配获取键值对
     *
     * @param key     键
     * @param options 匹配项
     * @return 键值对
     */
    public Cursor<ZSetOperations.TypedTuple<Object>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }
}